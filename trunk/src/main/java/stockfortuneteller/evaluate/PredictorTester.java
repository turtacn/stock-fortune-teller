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
    
    @Override
    public void execute() throws Exception {
        List<TestSequence<ObservationInteger>> testSequences = loadTestSequences(testSequencesDirName);
        Evaluation evaluation = evaluator.evaluate(predictor, testSequences);
        System.out.print(evaluation);
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

}
