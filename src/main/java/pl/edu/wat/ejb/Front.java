package pl.edu.wat.ejb;

import pl.edu.wat.dto.SimpleDto;

import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface Front {
    void transferDto(SimpleDto dto);
}
