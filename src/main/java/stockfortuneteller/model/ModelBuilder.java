/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.model;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.draw.GenericHmmDrawerDot;
import be.ac.ulg.montefiore.run.jahmm.learn.KMeansLearner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import stockfortuneteller.app.ExecutableBean;
import stockfortuneteller.app.Utils;

/**
 *
 * @author Adrian
 */
public class ModelBuilder implements ExecutableBean {
    private int modelStateNumber;
    private String sequencesInDirName;
    private String modelOutFileName;
    private String diagramOutFileName;
    private List<ModelPostprocessor<ObservationInteger>> postprocessors;
    
    @Override
    public void execute() throws Exception {
        Hmm<ObservationInteger> model = buildModel();
        Utils.serialize(getModelOutFileName(), model);
        
        GenericHmmDrawerDot drawer = new GenericHmmDrawerDot();
        drawer.write(model, getDiagramOutFileName());
    }

    private Hmm<ObservationInteger> buildModel() throws IOException {
        ArrayList<ArrayList<ObservationInteger>> sequences = Observations.loadSequencesDir(getSequencesInDirName());
        
        // initial model
        KMeansLearner<ObservationInteger> kMeansLearner = new KMeansLearner<>(getModelStateNumber(), new OpdfIntegerFactory(Observations.MAX_OBSERVATION_ID), sequences);
        Hmm<ObservationInteger> hmm = kMeansLearner.learn();
        
        // posprocess
        for(ModelPostprocessor<ObservationInteger> postprocessor: getPostprocessors()) {
            hmm = postprocessor.postprocess(hmm, sequences);
        }
        return hmm;
    }
    
    public int getModelStateNumber() {
        return modelStateNumber;
    }

    public void setModelStateNumber(int modelStateNumber) {
        this.modelStateNumber = modelStateNumber;
    }

    public String getModelOutFileName() {
        return modelOutFileName;
    }

    public void setModelOutFileName(String modelOutFileName) {
        this.modelOutFileName = modelOutFileName;
    }

    public String getDiagramOutFileName() {
        return diagramOutFileName;
    }

    public void setDiagramOutFileName(String diagramOutFileName) {
        this.diagramOutFileName = diagramOutFileName;
    }

    public List<ModelPostprocessor<ObservationInteger>> getPostprocessors() {
        return postprocessors;
    }

    public void setPostprocessors(List<ModelPostprocessor<ObservationInteger>> postprocessors) {
        this.setPostprocessors(postprocessors);
    }

    public String getSequencesInDirName() {
        return sequencesInDirName;
    }

    public void setSequencesInDirName(String sequencesInDirName) {
        this.sequencesInDirName = sequencesInDirName;
    }
}
