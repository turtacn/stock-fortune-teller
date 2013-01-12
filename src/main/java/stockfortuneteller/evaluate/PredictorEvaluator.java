/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.evaluate;

import be.ac.ulg.montefiore.run.jahmm.Observation;
import java.util.List;
import stockfortuneteller.predict.Predictor;

/**
 *
 * @author Adrian
 */
public interface PredictorEvaluator<O extends Observation> {
    public Evaluation evaluate(Predictor predictior, List<TestSequence<O>> sequences);
}
