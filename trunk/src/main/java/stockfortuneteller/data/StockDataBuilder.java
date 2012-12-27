/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockfortuneteller.data;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stockfortuneteller.app.ExecutableBean;

/**
 *
 * @author Itosu
 */
@Service
public class StockDataBuilder  implements ExecutableBean {
    
    private List<Company> companies;
    public void execute() throws Exception {
        List<Company> test = getCompanies();
        
        throw new UnsupportedOperationException("Not supported yet.");
        
    }    

    /**
     * @return the companies
     */
    public List<Company> getCompanies() {
        return companies;
    }

    /**
     * @param companies the companies to set
     */
    @Autowired
    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
