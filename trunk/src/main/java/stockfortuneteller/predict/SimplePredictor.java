/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.predict;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.Opdf;
import java.util.List;
import stockfortuneteller.app.Utils;
import stockfortuneteller.model.Observations;

/**
 *
 * @author Adrian
 */
public class SimplePredictor implements Predictor<ObservationInteger> {

    private String modelFile;
    private Hmm<ObservationInteger> model;
    private double simulationThreshold = 0.00001;

    @Override
    public Prediction predict(List<ObservationInteger> observations) {
        ensureModelIsLoaded();
        
        double increaseProbability = 0.0;
        double decreaseProbability = 0.0;
        double[] stateProbabilities = calculateStateProbabilities(observations);

        // calculate probability of first increase/decrese message appearing in future sequence
        while (arraySum(stateProbabilities) > getSimulationThreshold()) {

            // probability of current observation being increase/decrease
            for (int s = 0; s < model.nbStates(); ++s) {
                Opdf<ObservationInteger> opdf = model.getOpdf(s);
                double stateIncreaseProbability = opdf.probability(Observations.INCREASE_OBSERVATION);
                double stateDecreaseProbability = opdf.probability(Observations.DECREASE_OBSERVATION);
                
                increaseProbability += stateIncreaseProbability * stateProbabilities[s];
                decreaseProbability += stateDecreaseProbability * stateProbabilities[s];
                stateProbabilities[s] *= 1 - (stateIncreaseProbability + stateDecreaseProbability); // we are interesed only in first message
            }

            // move to next observation
            double[] nextStateProbabilities = new double[model.nbStates()];
            for (int source = 0; source < model.nbStates(); ++source) {
                for (int target = 0; target < model.nbStates(); ++target) {
                    nextStateProbabilities[target] += stateProbabilities[source] * model.getAij(source, target);
                }
            }
            stateProbabilities = nextStateProbabilities;
        }

        return new Prediction(increaseProbability, decreaseProbability);
    }

    private double arraySum(double[] array) {
        double sum = 0.0;
        for (int s = 0; s < array.length; ++s) {
            sum += array[s];
        }
        return sum;
    }

    // FUTURE: this could be calculated exactly using Viterbi algorithm, 
    // although jahmm doesnt provide such method, so we pretend do be in most likely state
    // with probability of 1.0
    private double[] calculateStateProbabilities(List<ObservationInteger> observations) {
        int[] mostLikelyStates = model.mostLikelyStateSequence(observations); assert (mostLikelyStates.length > 0);
        int mostLikelyState = mostLikelyStates[mostLikelyStates.length - 1]; // index of the last state
        
        double[] stateProbabilities = new double[model.nbStates()];
        stateProbabilities[mostLikelyState] = 1.0; 
        return stateProbabilities;
    }

    private void ensureModelIsLoaded() {
        if (model == null) {
            model = Utils.deserialize(getModelFile(), Hmm.class);
        }
    }

    /**
     * @return the modelFile
     */
    public String getModelFile() {
        return modelFile;
    }

    /**
     * @param modelFile the modelFile to set
     */
    public void setModelFile(String modelFile) {
        this.modelFile = modelFile;
    }

    /**
     * @return the simulationThreshold
     */
    public double getSimulationThreshold() {
        return simulationThreshold;
    }

    /**
     * @param simulationThreshold the simulationThreshold to set
     */
    public void setSimulationThreshold(double simulationThreshold) {
        this.simulationThreshold = simulationThreshold;
    }
}
