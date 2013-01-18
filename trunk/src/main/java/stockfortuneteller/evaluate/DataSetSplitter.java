/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.evaluate;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import stockfortuneteller.app.ExecutableBean;
import stockfortuneteller.model.Observations;

/**
 *
 * @author Adrian
 */
public class DataSetSplitter implements ExecutableBean {

    private String dataDirectory;
    private String trainDirectory;
    private String testDirectory;
    private int sequencesSize;
    private float testRatio;

    @Override
    public void execute() throws Exception {
        File trainDir = new File(getTrainDirectory());
        File testDir = new File(getTestDirectory());

        // recreate directories
        FileUtils.deleteDirectory(trainDir);
        FileUtils.deleteDirectory(testDir);
        trainDir.mkdir();
        testDir.mkdir();

        // read all data
        ArrayList<ArrayList<ObservationInteger>> data = Observations.loadSequencesDir(getDataDirectory());
        ArrayList<List<ObservationInteger>> result = new ArrayList<>();

        // split sequences
        for (ArrayList<ObservationInteger> sequence : data) {
            
            for (int startIndex = 0, endIndex = getSequencesSize() * 2; // NOTE *2 because we also take message+increase 
                    endIndex < sequence.size(); 
                    ++startIndex, ++endIndex) {
                result.add(sequence.subList(startIndex, endIndex));
            }
        }

        // randomly permutate sequences
        Collections.shuffle(result);

        // split
        int splitIndex = (int) (result.size() * getTestRatio());
        List<List<ObservationInteger>> testSet = result.subList(0, splitIndex);
        List<List<ObservationInteger>> trainSet = result.subList(splitIndex, result.size());

        // save
        save(trainSet, trainDir);
        save(testSet, testDir);
    }

    private void save(List<List<ObservationInteger>> set, File dir) throws IOException {
        assert (set.size() % 2 == 0);

        int fileCounter = 0;
        for (List<ObservationInteger> sequence : set) {
            ++fileCounter;

            File file = new File(dir, String.valueOf(fileCounter) + ".csv");
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("unused1,messageId,unused2,increaseId\n");

                Iterator<ObservationInteger> iterator = sequence.iterator();
                while (iterator.hasNext()) {
                    ObservationInteger messageId = iterator.next();
                    ObservationInteger increaseId = iterator.next();

                    writer.append("0," + messageId + ",0," + increaseId + "\n");
                }
            }
        }
    }

    /**
     * @return the dataDirectory
     */
    public String getDataDirectory() {
        return dataDirectory;
    }

    /**
     * @param dataDirectory the dataDirectory to set
     */
    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    /**
     * @return the trainDirectory
     */
    public String getTrainDirectory() {
        return trainDirectory;
    }

    /**
     * @param trainDirectory the trainDirectory to set
     */
    public void setTrainDirectory(String trainDirectory) {
        this.trainDirectory = trainDirectory;
    }

    /**
     * @return the testDirectory
     */
    public String getTestDirectory() {
        return testDirectory;
    }

    /**
     * @param testDirectory the testDirectory to set
     */
    public void setTestDirectory(String testDirectory) {
        this.testDirectory = testDirectory;
    }

    /**
     * @return the sequencesSize
     */
    public int getSequencesSize() {
        return sequencesSize;
    }

    /**
     * @param sequencesSize the sequencesSize to set
     */
    public void setSequencesSize(int sequencesSize) {
        this.sequencesSize = sequencesSize;
    }

    /**
     * @return the testRatio
     */
    public float getTestRatio() {
        return testRatio;
    }

    /**
     * @param testRatio the testRatio to set
     */
    public void setTestRatio(float testRatio) {
        this.testRatio = testRatio;
    }
}
