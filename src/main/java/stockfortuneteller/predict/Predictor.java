/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.predict;

import be.ac.ulg.montefiore.run.jahmm.Observation;
import java.util.List;

/**
 *
 * @author Adrian
 */
public interface Predictor<O extends Observation> {
    public Prediction predict(List<O> observations);
}
