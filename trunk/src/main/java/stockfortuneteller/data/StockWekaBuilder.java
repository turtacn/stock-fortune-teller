/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import stockfortuneteller.app.ExecutableBean;
import weka.clusterers.Cobweb;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

/**
 *
 * @author Itosu
 */
public class StockWekaBuilder implements ExecutableBean {

    private int numberOfClusters;
    private String arffFileName;
    private String csvResultFileName;

    public void execute() throws Exception {

        BufferedReader reader = new BufferedReader(
                new FileReader(getArffFileName()));
        Instances data = new Instances(reader);
        reader.close();

        Cobweb cobweb = new Cobweb();

        cobweb.setSeed(10);
        cobweb.setSaveInstanceData(true);

        cobweb.buildClusterer(data);

        FileWriter writer = new FileWriter(getCsvResultFileName());

        NumericToNominal nm = new NumericToNominal();
        String[] options = new String[2];
        options[0] = "-R";
        options[1] = "1";
        nm.setInputFormat(data);
        Instances filteredData = Filter.useFilter(data, nm);

        for (int i = data.numInstances() - 1; i > -1; i--) {

            writer.append(String.valueOf(cobweb.clusterInstance(data.instance(i))));
            writer.append(" ");
            writer.append(filteredData.instance(i).stringValue(1));
            writer.append('\n');
        }

        writer.flush();
        writer.close();
    }

    /**
     * @return the numberOfClusters
     */
    public int getNumberOfClusters() {
        return numberOfClusters;
    }

    /**
     * @param numberOfClusters the numberOfClusters to set
     */
    public void setNumberOfClusters(int numberOfClusters) {
        this.numberOfClusters = numberOfClusters;
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
}
