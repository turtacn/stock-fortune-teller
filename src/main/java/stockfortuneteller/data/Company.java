/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.data;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Itosu
 */
public class Company {
    private String name;
    private String infoURL;

    /**
     * @return the InfoURL - Komunikat
     */
    public String getInfoURL() {
        return infoURL;
    }

    /**
     * @param InfoURL the InfoURL to set
     */
    @Autowired
    public void setInfoURL(String InfoURL) {
        this.infoURL = InfoURL;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return name;
    }

    /**
     * @param Name the Name to set
     */
    @Autowired
    public void setName(String Name) {
        this.name = Name;
    }
}
