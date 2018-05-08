package pl.edu.wat.client;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import pl.edu.wat.dto.SimpleDto;
import pl.edu.wat.ejb.Front;
import pl.edu.wat.ejb.impl.FrontBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class);
    private static final Integer NUMBER_OF_THREADS = 50;
    private static Front front;


    public static void main(String[] args) {
        getBean();
        sendDTOs();
    }

    @SneakyThrows
    private static void getBean() {
        InitialContext ctx = getContext();
        front = (Front)ctx.lookup("ejb:/TiM-EJB-1.0-SNAPSHOT//" +FrontBean.class.getSimpleName()+"!"+Front.class.getName());
    }

    @SneakyThrows
    private static InitialContext getContext() {
        final Properties prop = new Properties();
        prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        prop.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
        return new InitialContext(prop);
    }

    @SneakyThrows
    private static void sendDTOs() {
        Random generator = new Random();
        for (int i = 1; i <= NUMBER_OF_THREADS; i++) {
            LOGGER.info("CREATING Thread no. " + i);
            Thread thread = new Thread("Thread no. " + i) {
                public void run() {
                    LOGGER.info("TRANSFERRING " + getName());
                    front.transferDto(new SimpleDto(new Date(), getName(),"Random content no. " + generator.nextInt()));
                }
            };
            thread.run();
            Thread.sleep(2000);
        }
        LOGGER.info("TRANSFER COMPLETE");
    }
}
