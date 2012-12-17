package stockfortuneteller.app;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class Main {

    static final protected String ConfigFileLocation = "app.config.xml";

    private List<ExecutableBean> tasks;
    
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(ConfigFileLocation);
        
        try {
            Main mainBean = applicationContext.getBean(Main.class); assert(mainBean != null);
            for (ExecutableBean task : mainBean.getTasks()) {
                task.execute();
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        applicationContext.close();
    }

    public List<ExecutableBean> getTasks() {
        return tasks;
    }

    public void setTasks(List<ExecutableBean> tasks) {
        this.tasks = tasks;
    }
}
