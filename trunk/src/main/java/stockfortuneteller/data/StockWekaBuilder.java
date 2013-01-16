/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import stockfortuneteller.app.ExecutableBean;
import weka.classifiers.trees.J48;
import weka.clusterers.Cobweb;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *
 * @author Itosu
 */
public class StockWekaBuilder implements ExecutableBean {

    private String arffFileName;
    private String csvResultFileName;
    private double acuity;
    private double cutoff;
    private int seed;

    public void execute() throws Exception {

        BufferedReader reader = new BufferedReader(
                new FileReader(getArffFileName()));
        Instances data = new Instances(reader);
        reader.close();

        Cobweb cobweb = new Cobweb();
        cobweb.setSeed(getSeed());
        cobweb.setAcuity(getAcuity());
        cobweb.setCutoff(getCutoff());
        cobweb.setSaveInstanceData(false);

        Remove removeFilter = new Remove();
        String[] options = new String[2];
        options[0] = "-R";
        options[1] = "2-3";
        removeFilter.setOptions(options);
        removeFilter.setInputFormat(data);

        Instances instNew = Filter.useFilter(data, removeFilter);

         Instances newInsts = new Instances(data);
         StringToWordVector stringFilter = new StringToWordVector();
         stringFilter.setInputFormat(newInsts);
         newInsts = Filter.useFilter(newInsts, stringFilter);
       
        cobweb.buildClusterer(newInsts);
        
        NumericToNominal nm = new NumericToNominal();
        nm.setInputFormat(data);
        Instances filteredData = Filter.useFilter(data, nm);
        Integer previousFileId = Integer.parseInt(filteredData.instance(data.numInstances() - 1).stringValue(2));
        Integer currentFileId;

        System.out.println("ILOSC: " + cobweb.numberOfClusters());
        FileWriter writer = new FileWriter(getCsvResultFileName() + String.valueOf(previousFileId) + ".csv");

        int id = 0;


        for (int i = data.numInstances() - 1; i > -1; i--) {

            currentFileId = Integer.parseInt(filteredData.instance(i).stringValue(2));

            if (previousFileId != currentFileId) {
                previousFileId = currentFileId;
                writer.flush();
                writer.close();
                writer = new FileWriter(getCsvResultFileName() + String.valueOf(currentFileId) + ".csv");
            }

            writer.append(String.valueOf(id));
            writer.append(",");
            id++;

            writer.append(String.valueOf(cobweb.clusterInstance(data.instance(i)) + 2));
            writer.append(",");
            
            //delete 2 lines below to get rid of text
            writer.append(data.instance(i).stringValue(0));
            writer.append(",");
            
            writer.append(filteredData.instance(i).stringValue(1));
            writer.append('\n');
        }

        writer.flush();
        writer.close();
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

    /**
     * @return the csvResultFileName
     */
    public String getCsvResultFileName() {
        return csvResultFileName;
    }

    /**
     * @param csvResultFileName the csvResultFileName to set
     */
    public void setCsvResultFileName(String csvResultFileName) {
        this.csvResultFileName = csvResultFileName;
    }

    /**
     * @return the cutoff
     */
    public double getCutoff() {
        return cutoff;
    }

    /**
     * @param cutoff the cutoff to set
     */
    public void setCutoff(double cutoff) {
        this.cutoff = cutoff;
    }

    /**
     * @return the seed
     */
    public int getSeed() {
        return seed;
    }

    /**
     * @param seed the seed to set
     */
    public void setSeed(int seed) {
        this.seed = seed;
    }

    /**
     * @return the acuity
     */
    public double getAcuity() {
        return acuity;
    }

    /**
     * @param acuity the acuity to set
     */
    public void setAcuity(double acuity) {
        this.acuity = acuity;
    }
}
