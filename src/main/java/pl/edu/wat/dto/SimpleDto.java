package pl.edu.wat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleDto implements Serializable {
    Date transferDate;
    String threadName;
    String randomContent;
}
