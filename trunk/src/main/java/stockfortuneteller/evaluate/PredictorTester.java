/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.evaluate;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import java.util.List;
import stockfortuneteller.app.ExecutableBean;
import stockfortuneteller.predict.Predictor;

/**
 *
 * @author Adrian
 */
public class PredictorTester implements ExecutableBean {

    private PredictorEvaluator<ObservationInteger> evaluator;
    private Predictor<ObservationInteger> predictor;
    
    private String sequencesInFileName;
    
    
    @Override
    public void execute() throws Exception {
        List<TestSequence<ObservationInteger>> testSequences = loadTestSequences(sequencesInFileName);
        Evaluation evaluation = evaluator.evaluate(predictor, testSequences);
        System.out.print(evaluation);
    }

    private List<TestSequence<ObservationInteger>> loadTestSequences(String sequencesInFileName) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
