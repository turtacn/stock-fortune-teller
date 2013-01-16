/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.predict;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.Opdf;
import java.util.ArrayList;
import java.util.List;
import stockfortuneteller.app.Utils;
import stockfortuneteller.model.Observations;
import weka.core.SerializationHelper;

/**
 *
 * @author Adrian
 */
public class SimplePredictor implements Predictor<ObservationInteger> {
    
    private Hmm<ObservationInteger> model;
    
    public SimplePredictor(String modelFilePath) throws Exception {
        model = Utils.deserialize(modelFilePath, Hmm.class); 
    }
   
    @Override
    public Prediction predict(List<ObservationInteger> observations) {
        int[] mostLikelyStates = model.mostLikelyStateSequence(observations); assert(mostLikelyStates.length > 0);
        int mostLikelyState = mostLikelyStates[mostLikelyStates.length - 1];
        
        // get distribution of next increase/decrease message
        Opdf<ObservationInteger> opdf = model.getOpdf(mostLikelyState);
        double increaseProbability = opdf.probability(Observations.INCREASE_OBSERVATION);
        double decreaseProbability = opdf.probability(Observations.DECREASE_OBSERVATION);
        return new Prediction(increaseProbability, decreaseProbability);
    }
}
