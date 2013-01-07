/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.data;

import java.io.File;
import org.springframework.stereotype.Service;
import stockfortuneteller.app.ExecutableBean;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
/**
 *
 * @author Itosu
 */
@Service
public class StockWekaARFFBuilder implements ExecutableBean  {
    
     public void execute() throws Exception {
         
    // load CSV
    CSVLoader loader = new CSVLoader();
    loader.setSource(new File("src/main/resources/info.csv"));
    Instances data = loader.getDataSet();
    
     // save ARFF
    ArffSaver saver = new ArffSaver();
    saver.setInstances(data);
    saver.setFile(new File("src/main/resources/info.arff"));
   // saver.setDestination(new File("src/main/resources/info.arff"));
    saver.writeBatch();
    
     }
     
}
