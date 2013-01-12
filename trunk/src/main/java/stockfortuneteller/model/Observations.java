/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.model;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import java.util.ArrayList;

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
    
    public static ArrayList<ArrayList<ObservationInteger>> loadSequences(String filename) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: finish observation
    }
}
