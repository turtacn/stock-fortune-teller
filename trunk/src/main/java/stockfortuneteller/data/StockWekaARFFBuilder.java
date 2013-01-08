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
    
    private String csvFileName;
    private String arffFileName;
    
     public void execute() throws Exception {
         
    // load CSV
    CSVLoader loader = new CSVLoader();
    loader.setSource(new File(getCsvFileName()));
    Instances data = loader.getDataSet();
    
     // save ARFF
    ArffSaver saver = new ArffSaver();
    saver.setInstances(data);
    saver.setFile(new File(getArffFileName()));
    saver.writeBatch();
    
     }

    /**
     * @return the csvFileName
     */
    public String getCsvFileName() {
        return csvFileName;
    }

    /**
     * @param csvFileName the csvFileName to set
     */
    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    /**
     * @return the arffFileName
     */
    public String getArffFileName() {
        return arffFileName;
    }

    /**
     * @param arffFileName the arffFileName to set
     */
    public void setArffFileName(String arffFileName) {
        this.arffFileName = arffFileName;
    }
     
}
