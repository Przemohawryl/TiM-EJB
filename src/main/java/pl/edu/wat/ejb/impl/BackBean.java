package pl.edu.wat.ejb.impl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.log4j.Logger;
import pl.edu.wat.dto.SimpleDto;
import pl.edu.wat.entity.SimpleEntity;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;

@Singleton
public class BackBean {
    private static final Logger LOGGER = Logger.getLogger(BackBean.class);
    private static final String FILE_PATH = "/files";
    private static XStream XML_STREAM;
    private static EntityManager entityManager;
    private static Integer counter = 0;

    @Schedule(second = "*/10", minute = "*", hour = "*")
    private void checkExistsFiles() {
        LOGGER.info("CHECKING FILES NO. " + ++counter);

        File folder = new File(FILE_PATH);
        File[] listOfFiles = folder.listFiles();
        int filesCount;
        if (listOfFiles != null) {
            filesCount = listOfFiles.length;

            LOGGER.info("EXISTS: " + filesCount + " FILES");

            saveFilesToDatabase(listOfFiles);
        }
    }

    private void saveFilesToDatabase(File[] listOfFiles) {
        LOGGER.info("SAVING TO DATABASE: " + listOfFiles.length + " FILES");
        for (File file : listOfFiles) {
            SimpleDto simpleDto = deserializeDTO(file);
            SimpleEntity entity = buildSimpleEntity(simpleDto);
            createEntityManager();
            saveEntity(entity);

            deleteFile(file);
        }
    }

    private SimpleDto deserializeDTO(File file) {
        if (XML_STREAM == null) XML_STREAM = new XStream(new DomDriver());

        return (SimpleDto)XML_STREAM.fromXML(file);
    }

    private SimpleEntity buildSimpleEntity(SimpleDto simpleDto) {
        return SimpleEntity.builder()
                .name(simpleDto.getThreadName())
                .date(simpleDto.getTransferDate())
                .content(simpleDto.getRandomContent())
                .build();
    }

    private void createEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
            entityManager = emf.createEntityManager();
        }
    }

    private void saveEntity(SimpleEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        LOGGER.info("SAVING COMPLETE");
    }

    private void deleteFile(File file) {
        LOGGER.info("REMOVE FILE: " + file.getName());
        if (file.delete()) {
            LOGGER.info("FILE DELETED!");
        }
    }
}
