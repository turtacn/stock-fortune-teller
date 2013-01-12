/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.model.postprocessor;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.Observation;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import java.util.List;
import stockfortuneteller.model.ModelPostprocessor;

/**
 *
 * @author Adrian
 */
public class BwlPostprocessor<O extends Observation> implements ModelPostprocessor<O> {

    @Override
    public Hmm<O> postprocess(Hmm<O> model, List<? extends List<? extends O>> sequences) {
        // go to local minimum
        BaumWelchLearner bwl = new BaumWelchLearner();
        Hmm<O> bwlModel = bwl.learn(model, sequences);
        return bwlModel;
    }
    
}
