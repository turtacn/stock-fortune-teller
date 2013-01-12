/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author Adrian
 */
public class Utils {
    public static void serialize(String fileName, Serializable o) {
        try(FileOutputStream fos = new FileOutputStream(fileName)) {
            SerializationUtils.serialize(o, fos);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static <T> T deserialize(String fileName, Class<T> cls) {
        try(FileInputStream fis = new FileInputStream(fileName)) {
            return (T)SerializationUtils.deserialize(fis);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
