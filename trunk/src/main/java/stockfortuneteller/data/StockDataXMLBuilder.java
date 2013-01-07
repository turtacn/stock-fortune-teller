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
import morfologik.stemming.PolishStemmer;
import morfologik.stemming.WordData;
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
public class StockDataXMLBuilder implements ExecutableBean {


    public void execute() throws Exception {

      String test = "GRUPA LOTOS SA zawarcie umów o wartości znaczącej pomiędzy Grupą LOTOS SA a Mercuria Energy Trading SA";
      String returns = StemSentence(test);
      
    }

       private String StemSentence(String sentence) {
        String[] sentenceTable = sentence.split(" ");
        StringBuilder stemmedSentence = new StringBuilder();

        PolishStemmer stemmer = new PolishStemmer();
        String stem = "";
        WordData temp;
        for (int i = 0; i < sentenceTable.length; i++) {
            try {
                String temp2 = sentenceTable[i].toLowerCase();
                List<WordData> test = stemmer.lookup(temp2);
                temp = test.get(0);
                stem = temp.getStem().toString();
                
            } catch (Exception ex) {
                stem = sentenceTable[i];
            } finally {
                stemmedSentence.append(stem);
                stemmedSentence.append(" ");
            }
        }
        stemmedSentence.deleteCharAt(stemmedSentence.length() - 1);

        return stemmedSentence.toString();
    }
       
}
