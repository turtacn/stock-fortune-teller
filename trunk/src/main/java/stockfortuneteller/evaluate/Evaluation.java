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
    private int[][] confusionMatrix = new int[2][2];

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Evaluation\n")
            .append("\n")
            .append("TRUE POSITIVES - ").append(tp()).append("\n")
            .append("TRUE NEGATIVES - ").append(tn()).append("\n") 
            .append("FALSE POSITIVES - ").append(fp()).append("\n") 
            .append("FALSE NEGATIVES - ").append(fn()).append("\n") 
            .append("\n")
            .append("ACCURACY - ").append(accuracy()).append("\n")   
            .append("RECALL - ").append(recall()).append("\n") 
            .append("PRECISION - ").append(precision()).append("\n")
            .append("SPECIFICITY - ").append(specificity()).append("\n")
        ;   
        
        return sb.toString();
    }

    public void addPrediction(Prediction prediction, boolean willIncrease) {
        ++totalPredictions;
        ++confusionMatrix[willIncrease ? 1 : 0][prediction.willIncrease() ? 1 : 0];
    }
    
    public String simulate(float cash, float gainRatio, float loseRatio) {
        float gained = cash * tp()/totalPredictions * gainRatio;
        float lost = cash * fp()/totalPredictions * loseRatio;
        float notlost = cash * tn()/totalPredictions * loseRatio;
        float notgained = cash * fn()/totalPredictions * gainRatio;
        float delta = gained - lost;
        float total = cash + gained - lost;
        float bestGain = gained + notgained;
        float worstLose = lost + notlost;
        float efficency =  (delta + worstLose) / (bestGain + worstLose);
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Simulation\n")
            .append("\n")
            .append("INITIAL CASH - ").append(cash).append("\n")
            .append("\n")
            .append("GAINED - ").append(gained).append("\n")
            .append("LOST - ").append(lost).append("\n") 
            .append("DELTA - ").append(delta).append("\n") 
            .append("NOT LOST (SAVED) - ").append(notlost).append("\n") 
            .append("NOT GAINED (MISSED) - ").append(notgained).append("\n") 
            .append("\n")
            .append("TOTAL CASH - ").append(total).append("\n") 
            .append("BEST CASH - ").append(cash + bestGain).append("\n") 
            .append("WORST CASH - ").append(cash - worstLose).append("\n") 
            .append("BEST GAIN RATIO - ").append(delta / bestGain).append("\n") 
            .append("EFFICENCY - ").append(efficency).append("\n") 
        ;   
        
        return sb.toString();
    }   
    
    public float tp() {
        return confusionMatrix[1][1];
    }
    
    public float tn() {
        return confusionMatrix[0][0];
    }
    
    public float fp() {
        return confusionMatrix[1][0];
    }
    
    public float fn() {
        return confusionMatrix[0][1];
    }
    
    public float p() {
        return tp() + fn();
    }
    
    public float n() {
        return tn() + fp();
    }
    
    public float accuracy() {
        return (tp() + tn()) / totalPredictions;
    }
    
    public float recall() {
        return tpRate();
    }
    
    public float specificity() {
        return 1 - fpRate();
    }
    
    public float precision() {
        return tp()/(tp() + fp());
    }
    
    public float tpRate() {
        return tp()/p();
    }
    
    public float fpRate() {
        return fp()/n();
    }
    
    
    
    
}
