package pl.edu.wat.client;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.wildfly.naming.client.WildFlyInitialContext;
import pl.edu.wat.dto.SimpleDto;
import pl.edu.wat.ejb.Front;
import pl.edu.wat.ejb.impl.FrontBean;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Random;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class);
    private static final Integer NUMBER_OF_THREADS = 50;
//    @EJB
//    private static Front front;

    @SneakyThrows
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        prop.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        prop.put("jboss.naming.client.ejb.context", false);
        InitialContext ctx = new InitialContext(prop);
        Front front = (Front)ctx.lookup("ejb:/TiM-EJB-1.0-SNAPSHOT/" +Front.class.getSimpleName()+"!"+Front.class.getName());

        for (int i = 1; i <= NUMBER_OF_THREADS; i++) {
            LOGGER.info("CREATING Thread no. " + i);
            Thread thread = new Thread("Thread no. " + i) {
                public void run() {
                    Random generator = new Random();
                    LOGGER.info("TRANSFERRING " + getName());
                    front.transferDto(new SimpleDto(new Date(), getName(),"Random content no. " + generator.nextInt()));
                }
            };
            thread.run();
        }
        LOGGER.info("TRANSFER COMPLETE");
    }
}
