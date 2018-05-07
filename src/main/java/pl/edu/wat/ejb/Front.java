package pl.edu.wat.ejb;

import pl.edu.wat.dto.SimpleDto;

import javax.ejb.LocalBean;
import javax.ejb.Remote;

@LocalBean
@Remote
public interface Front {
    void transferDto(SimpleDto dto);
}
