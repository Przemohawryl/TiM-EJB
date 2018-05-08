package pl.edu.wat.ejb.impl;

import org.apache.log4j.Logger;
import pl.edu.wat.dto.SimpleDto;
import pl.edu.wat.ejb.Front;
import pl.edu.wat.ejb.Middle;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
@Asynchronous
public class FrontBean implements Front {
    private static final Logger LOGGER = Logger.getLogger(FrontBean.class);

    @EJB
    private Middle middleBean;

    @Override
    @Asynchronous
    public void transferDto(SimpleDto dto) {
        LOGGER.info("RECEIVED DTO: {transferDate: " + dto.getTransferDate() + ", threadName: " + dto.getThreadName() + ", randomContent: " + dto.getRandomContent() + "}.");
        LOGGER.info("TRANSFERRING DTO: " + dto.getThreadName() + " to MiddleBean");
        middleBean.transferDto(dto);
        LOGGER.info("TRANSFER COMPLETE");
    }
}
