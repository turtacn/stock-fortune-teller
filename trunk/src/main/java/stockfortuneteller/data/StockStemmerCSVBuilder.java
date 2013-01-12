/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import morfologik.stemming.PolishStemmer;
import morfologik.stemming.WordData;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;
import stockfortuneteller.app.ExecutableBean;

/**
 *
 * @author Itosu
 */
public class StockStemmerCSVBuilder implements ExecutableBean {

    private List<Company> companies;
    private ArrayList<String> forbiddenWords;
    private String fileName;

    @Override
    public void execute() throws Exception {

        FileWriter writer = new FileWriter(getFileName());
        writer.append("tekst,change");
        writer.append('\n');

        try {

            for (Iterator<Company> i = getCompanies().iterator(); i.hasNext();) {
                Company company = i.next();

                String url = company.getInfoURL();
                org.jsoup.nodes.Document page;

                while (true) {

                    try {
                        page = Jsoup.connect(url).timeout(0).userAgent("Mozilla").get();

                        Elements trs = page.select("table.tabela tr");
                        Elements table = page.select("table.tabela");

                        if (!trs.isEmpty() && table.hasClass("tlo_biel")) {
                            //remove header row
                            trs.remove(0);
                            float changeValue;
                            String changeNumber;

                            for (org.jsoup.nodes.Element tr : trs) {
                                Elements tds = tr.getElementsByTag("td");
                                if (tds.size() >= 5) {

                                    org.jsoup.nodes.Element tdText = tds.get(2);
                                    org.jsoup.nodes.Element tdChange = tds.get(4);

                                    if (tdText.text().contains(company.getName()) && !tdChange.text().isEmpty()) {

                                        writer.append(StemSentence(tdText.text()));
                                        writer.append(",");
                                        changeValue = Float.parseFloat(tdChange.text().replace(',', '.'));
                                        if (changeValue > 0) {
                                            changeNumber = "-1";
                                        } else if (changeValue < 0) {
                                            changeNumber = "-2";
                                        } else {
                                            changeNumber = "-3";
                                        }
                                        writer.append(changeNumber);
                                        writer.append('\n');
                                    }
                                }
                            }
                        } else {
                            break;
                        }
                        url = GetNextPage(url);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String GetNextPage(String currentAddress) {
        String[] split = currentAddress.replaceFirst(".html", "").split(",,");
        int nextPage = Integer.parseInt(split[1]) + 1;
        return split[0] + ",," + String.valueOf(nextPage) + ".html";
    }

    private String StemSentence(String sentence) {
        String[] sentenceTable = sentence.split(" ");
        StringBuilder stemmedSentence = new StringBuilder();

        PolishStemmer stemmer = new PolishStemmer();
        String stem = "";

        for (int i = 0; i < sentenceTable.length; i++) {
            try {
                stem = stemmer.lookup(sentenceTable[i].toLowerCase()).get(0).getStem().toString();
            } catch (Exception ex) {
                stem = sentenceTable[i];
            } finally {
                if (!getForbiddenWords().contains(stem)) {
                    stemmedSentence.append(DeletePolishSigns(stem));
                    stemmedSentence.append(" ");
                }
            }
        }
        stemmedSentence.deleteCharAt(stemmedSentence.length() - 1);

        return stemmedSentence.toString();
    }

    private String DeletePolishSigns(String word) {
        return word.replace('ą', 'a').replace('ę', 'e').replace('ć', 'c').replace('ź', 'z').
                replace('ż', 'z').replace('ł', 'l').replace('ś', 's').replace('ó', 'o');
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
    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    /**
     * @return the forbiddenWords
     */
    public ArrayList<String> getForbiddenWords() {
        return forbiddenWords;
    }

    /**
     * @param forbiddenWords the forbiddenWords to set
     */
    public void setForbiddenWords(ArrayList<String> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
