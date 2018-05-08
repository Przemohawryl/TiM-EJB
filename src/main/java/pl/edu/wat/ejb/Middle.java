package pl.edu.wat.ejb;

import pl.edu.wat.dto.SimpleDto;

import javax.ejb.LocalBean;

@LocalBean
public interface Middle {
    void transferDto(SimpleDto dto);
}
