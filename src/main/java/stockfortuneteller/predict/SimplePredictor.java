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
import stockfortuneteller.model.Observations;

/**
 *
 * @author Adrian
 */
public class SimplePredictor implements Predictor<ObservationInteger> {
    
    private Hmm<ObservationInteger> model;
    
    @Override
    public Prediction predict(List<ObservationInteger> observations) {
        int[] mostLikelyStates = getModel().mostLikelyStateSequence(observations); assert(mostLikelyStates.length > 0);
        int mostLikelyState = mostLikelyStates[mostLikelyStates.length - 1];
        
        // get distribution of next increase/decrease message
        Opdf<ObservationInteger> opdf = getModel().getOpdf(mostLikelyState);
        double gainProbability = opdf.probability(Observations.INCREASE_OBSERVATION);
        double lossProbability = opdf.probability(Observations.DECREASE_OBSERVATION);
        
        return new Prediction(gainProbability, lossProbability);
    }

    public Hmm<ObservationInteger> getModel() {
        return model;
    }

    public void setModel(Hmm<ObservationInteger> model) {
        this.model = model;
    }
}
