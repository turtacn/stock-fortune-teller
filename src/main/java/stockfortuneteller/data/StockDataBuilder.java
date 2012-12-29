/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.data;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import stockfortuneteller.app.ExecutableBean;

/**
 *
 * @author Itosu
 */
@Service
public class StockDataBuilder implements ExecutableBean {

    private List<Company> companies;

    public void execute() throws Exception {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Infos");
            doc.appendChild(rootElement);

            for (Iterator<Company> i = getCompanies().iterator(); i.hasNext();) {
          
                Company company = i.next();
                
               
                
                //company elements
                Element companyElement = doc.createElement("Company");
                rootElement.appendChild(companyElement);

                //set attribute to staff element
                Attr attr = doc.createAttribute("name");
                attr.setValue(company.getName());
                companyElement.setAttributeNode(attr);

                
                String url = company.getInfoURL(); 
                org.jsoup.nodes.Document page;
               while(true)
               {
                        
                try {
                    page = Jsoup.connect(url).timeout(0).userAgent("Mozilla").get();
                  
                    Elements trs = page.select("table.tabela tr");
                    Elements table = page.select("table.tabela");

                    if (!trs.isEmpty() && table.hasClass("tlo_biel")) {
                        //remove header row
                        trs.remove(0);

                        for (org.jsoup.nodes.Element tr : trs) {
                            Elements tds = tr.getElementsByTag("td");
                            if (tds.size() >= 5) {
                                
                                org.jsoup.nodes.Element tdText = tds.get(2);
                                org.jsoup.nodes.Element tdChange = tds.get(4);
                                
                                if (tdText.text().contains(company.getName()) && !tdChange.text().isEmpty()) {

                                    Element info = doc.createElement("info");
                                    companyElement.appendChild(info);

                                    Element text = doc.createElement("text");
                                    text.appendChild(doc.createTextNode(tdText.text()));
                                    info.appendChild(text);

                                    
                                    Element change = doc.createElement("change");
                                    change.appendChild(doc.createTextNode(tdChange.text()));
                                    info.appendChild(change);

                                }
                            }
                        }
                    }
                    else 
                    {
                        break;
                    }
                    
                    url = GetNextPage(url);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            }

            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("src/main/resources/info.xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        throw new UnsupportedOperationException("Not supported yet.");

    }

    /**
     * according to the pattern: for
     * http://www.money.pl/gielda/komunikaty/strona,264,,1.html returns
     * http://www.money.pl/gielda/komunikaty/strona,264,,2.html
     */
    private String GetNextPage(String currentAddress) {
        String[] split = currentAddress.replaceFirst(".html", "").split(",,");
        int nextPage = Integer.parseInt(split[1]) + 1;
        return split[0] + ",," + String.valueOf(nextPage) + ".html";
    }
    /**
     * @return the companies
     */
    public List<Company> getCompanies() {
        return companies;
    }

    /**
     * @param companies the companies to set
     */
    @Autowired
    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
