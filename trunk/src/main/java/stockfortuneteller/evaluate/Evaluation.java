/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.evaluate;

import stockfortuneteller.predict.Prediction;

/**
 *
 * @author Adrian
 */
public class Evaluation {
    private int totalPredictions = 0;
    private Integer[][] confusionMatrix = new Integer[2][2];

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Evaluation\n")
            .append("\n")
            .append("TRUE POSITIVES - ").append(getTruePositives())
            .append("TRUE NEGATIVES - ").append(getTrueNegatives()) 
            .append("FALSE POSITIVES - ").append(getFalsePositives())  
            .append("FALSE NEGATIVES - ").append(getFalseNegatives()) 
            .append("\n")
            .append("ACCURACY - ").append(getAccuracy());   
        
        return super.toString();
    }

    public void addPrediction(Prediction prediction, boolean willIncrease) {
        ++totalPredictions;
        ++confusionMatrix[willIncrease ? 1 : 0][prediction.willIncrease() ? 1 : 0];
    }
    
    private int getTruePositives() {
        return confusionMatrix[1][1];
    }
    
    private int getTrueNegatives() {
        return confusionMatrix[0][0];
    }
    
    private int getFalsePositives() {
        return confusionMatrix[1][0];
    }
    
    private int getFalseNegatives() {
        return confusionMatrix[0][1];
    }
    
    private int getPositives() {
        return getTruePositives() + getFalsePositives();
    }
    
    private int getNegatives() {
        return getTrueNegatives() + getFalseNegatives();
    }
    
    public float getAccuracy() {
        return (getTruePositives() + getTrueNegatives()) / totalPredictions;
    }
    
    
}
