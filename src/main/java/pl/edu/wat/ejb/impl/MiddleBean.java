package pl.edu.wat.ejb.impl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import pl.edu.wat.dto.SimpleDto;
import pl.edu.wat.ejb.Middle;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
public class MiddleBean implements Middle {
    private static final Logger LOGGER = Logger.getLogger(MiddleBean.class);
    private static final String FILE_PATH = "/files/";
    private static final DateFormat formatter = new SimpleDateFormat("ddMMyyyy");

    @Override
    @Asynchronous
    public void transferDto(SimpleDto dto) {
        LOGGER.info("RECEIVED DTO: {transferDate: " + dto.getTransferDate() + ", threadName: " + dto.getThreadName() + ", randomContent: " + dto.getRandomContent() + "}.");
        saveFile(dto);
    }

    @SneakyThrows
    private void saveFile(SimpleDto dto) {
        String xml = dto2Xml(dto);
        String fileName = "ZGL_" + formatter.format(new Date()) + "_" + dto.hashCode() + ".xml";
        LOGGER.info("CREATING " + fileName + " file");
        String outputStreamName;

        if ((new File("/files")).mkdirs()) {
            outputStreamName = fileName;
        } else {
            outputStreamName = FILE_PATH + fileName;
        }

        try (FileOutputStream outputStream = new FileOutputStream(outputStreamName)) {
            outputStream.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
            byte[] bytes = xml.getBytes("UTF-8");
            outputStream.write(bytes);
        } catch (FileNotFoundException ignored) {}


    }

    private String dto2Xml(SimpleDto simpleDto) {
        LOGGER.info("SERIALIZATION " + simpleDto.getThreadName() + " to XML");
        XStream mapping = new XStream(new DomDriver());

        return mapping.toXML(simpleDto);
    }
}
