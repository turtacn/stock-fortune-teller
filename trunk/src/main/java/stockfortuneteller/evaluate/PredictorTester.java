/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.evaluate;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import stockfortuneteller.app.ExecutableBean;
import stockfortuneteller.model.Observations;
import stockfortuneteller.predict.Predictor;

/**
 *
 * @author Adrian
 */
public class PredictorTester implements ExecutableBean {

    private PredictorEvaluator<ObservationInteger> evaluator;
    private Predictor<ObservationInteger> predictor;
 
    private String testSequencesDirName;
    private float cash;
    private float gainRatio;
    private float loseRatio;
    
    @Override
    public void execute() throws Exception {
        List<TestSequence<ObservationInteger>> testSequences = loadTestSequences(getTestSequencesDirName());
        Evaluation evaluation = getEvaluator().evaluate(getPredictor(), testSequences);
        System.out.print(evaluation.toString());
        System.out.print(evaluation.simulate(getCash(), getGainRatio(), getLoseRatio()));
    }

    private List<TestSequence<ObservationInteger>> loadTestSequences(String testSequencesDirName) throws IOException {
        ArrayList<ArrayList<ObservationInteger>> sequences = Observations.loadSequencesDir(testSequencesDirName);
        List<TestSequence<ObservationInteger>> result = new ArrayList<>(sequences.size());
        
        for(ArrayList<ObservationInteger> sequence: sequences) {
            ObservationInteger lastElement = sequence.remove(sequence.size() - 1); // remove last element from sequence
            result.add(new TestSequence<>(sequence, lastElement.value == Observations.INCREASE_MESSAGE));
        }
        
        return result;
    }

    /**
     * @return the evaluator
     */
    public PredictorEvaluator<ObservationInteger> getEvaluator() {
        return evaluator;
    }

    /**
     * @param evaluator the evaluator to set
     */
    public void setEvaluator(PredictorEvaluator<ObservationInteger> evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * @return the predictor
     */
    public Predictor<ObservationInteger> getPredictor() {
        return predictor;
    }

    /**
     * @param predictor the predictor to set
     */
    public void setPredictor(Predictor<ObservationInteger> predictor) {
        this.predictor = predictor;
    }

    /**
     * @return the testSequencesDirName
     */
    public String getTestSequencesDirName() {
        return testSequencesDirName;
    }

    /**
     * @param testSequencesDirName the testSequencesDirName to set
     */
    public void setTestSequencesDirName(String testSequencesDirName) {
        this.testSequencesDirName = testSequencesDirName;
    }

    /**
     * @return the cash
     */
    public float getCash() {
        return cash;
    }

    /**
     * @param cash the cash to set
     */
    public void setCash(float cash) {
        this.cash = cash;
    }

    /**
     * @return the gainRatio
     */
    public float getGainRatio() {
        return gainRatio;
    }

    /**
     * @param gainRatio the gainRatio to set
     */
    public void setGainRatio(float gainRatio) {
        this.gainRatio = gainRatio;
    }

    /**
     * @return the loseRatio
     */
    public float getLoseRatio() {
        return loseRatio;
    }

    /**
     * @param loseRatio the loseRatio to set
     */
    public void setLoseRatio(float loseRatio) {
        this.loseRatio = loseRatio;
    }

}
