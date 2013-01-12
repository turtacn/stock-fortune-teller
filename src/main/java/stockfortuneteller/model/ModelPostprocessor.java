/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.model;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.Observation;
import java.util.List;

/**
 *
 * @author Adrian
 */
public interface ModelPostprocessor<O extends Observation> {
    Hmm<O> postprocess(Hmm<O> model, List<? extends List<? extends O>> sequences);
}
