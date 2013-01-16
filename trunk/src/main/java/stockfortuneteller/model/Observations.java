/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.model;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

/**
 *
 * @author Adrian
 */
public class Observations {

    public static final int INCREASE_MESSAGE = 0;
    public static final int DECREASE_MESSAGE = 1;
    public static final int MAX_OBSERVATION_ID = 500; // FUTURE: make this dynamic
    public static final ObservationInteger INCREASE_OBSERVATION = new ObservationInteger(INCREASE_MESSAGE);
    public static final ObservationInteger DECREASE_OBSERVATION = new ObservationInteger(DECREASE_MESSAGE);

    private static final int GROUP_COLUMN_INDEX = 1;
    private static final int CHANGE_COLUMN_INDEX = 3;
    
    public static ArrayList<ArrayList<ObservationInteger>> loadSequencesDir(String dirname) throws IOException {
        ArrayList<ArrayList<ObservationInteger>> result = new ArrayList<>();
        
        File dir = new File(dirname);
        for (File child : dir.listFiles()) {
            result.add(loadSequence(child));
        }
        
        return result;
    }
    
    public static ArrayList<ObservationInteger> loadSequence(File file) throws IOException {
        CSVLoader loader = new CSVLoader(); // NOTE: weka has convinient csvloader although it adds unnecessary dependency
        loader.setSource(file);
        Instances data = loader.getDataSet();
        
        ArrayList<ObservationInteger> result = new ArrayList<>(data.numInstances() * 2);
        for(int i = 0; i < data.numInstances(); ++i) {
            Instance instance = data.instance(i);
            result.add(new ObservationInteger((int)instance.value(GROUP_COLUMN_INDEX)));
            result.add(new ObservationInteger((int)instance.value(CHANGE_COLUMN_INDEX)));
        }
        
        return result;
    }
}
