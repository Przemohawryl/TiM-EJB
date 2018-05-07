package pl.edu.wat.ejb.impl;

import org.apache.log4j.Logger;
import pl.edu.wat.dto.SimpleDto;
import pl.edu.wat.ejb.Front;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
@Asynchronous
public class FrontBean implements Front {
    private static final Logger LOGGER = Logger.getLogger(FrontBean.class);

    @Override
    @Asynchronous
    public void transferDto(SimpleDto dto) {
        LOGGER.info("RECEIVED DTO: {transferDate: " + dto.getTransferDate() + ", threadName: " + dto.getThreadName() + ", randomContent: " + dto.getRandomContent() + "}.");
    }
}
