package pl.edu.wat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SimpleDto {
    Date transferDate;
    String threadName;
    String randomContent;
}
