/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.predict;

/**
 *
 * @author Adrian
 */
public class Prediction {

    private double increaseChance;
    private double decreaseChance;

    public Prediction(double increaseChance, double decreaseChance) {
        // normalize those since HMM stores proabilities for all messages
        double totalChance = increaseChance + decreaseChance;

        this.increaseChance = increaseChance / totalChance;
        this.decreaseChance = decreaseChance / totalChance;
    }

    public double getIncreaseChance() {
        return increaseChance;
    }

    public double getDecreaseChance() {
        return decreaseChance;
    }
    
    public boolean willIncrease() {
        // FUTURE: here we could also return DON'T KNOW if difference is below threshold
        return increaseChance > decreaseChance;
    } 
}
