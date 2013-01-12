/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.evaluate;

import be.ac.ulg.montefiore.run.jahmm.Observation;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class TestSequence<O extends Observation> {
    private List<? extends O> sequence;
    private boolean increase;

    public TestSequence(List<? extends O> sequence, boolean increase) {
        this.sequence = sequence;
        this.increase = increase;
    }

    public List<? extends O> getSequence() {
        return sequence;
    }

    public boolean isIncrease() {
        return increase;
    }
}
