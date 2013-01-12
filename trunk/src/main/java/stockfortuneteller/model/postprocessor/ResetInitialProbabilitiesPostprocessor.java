/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.model.postprocessor;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.Observation;
import java.util.List;
import stockfortuneteller.model.ModelPostprocessor;

/**
 *
 * @author Adrian
 */
public class ResetInitialProbabilitiesPostprocessor<O extends Observation> implements ModelPostprocessor<O> {

    @Override
    public Hmm<O> postprocess(Hmm<O> model, List<? extends List<? extends O>> sequences) {
        // reset all initial states, because we will predict using sequences starting from any point in time
        for(int state = 0; state < model.nbStates(); ++state) {
            model.setPi(state, 1.0 / model.nbStates());
        }
        
        return model;
    }
    
}
