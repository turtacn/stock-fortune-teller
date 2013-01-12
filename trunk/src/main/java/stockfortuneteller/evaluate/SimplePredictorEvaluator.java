/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.evaluate;

import be.ac.ulg.montefiore.run.jahmm.Observation;
import java.util.List;
import stockfortuneteller.predict.Prediction;
import stockfortuneteller.predict.Predictor;

/**
 *
 * @author Adrian
 */
public class SimplePredictorEvaluator<O extends Observation> implements PredictorEvaluator<O> {
    @Override
    public Evaluation evaluate(Predictor predictior, List<TestSequence<O>> sequences) {
        Evaluation result = new Evaluation();
        
        for(TestSequence<O> ts: sequences) {
            Prediction prediction = predictior.predict(ts.getSequence());
            result.addPrediction(prediction, ts.isIncrease());
        }

        return result;
    }
}
