package pl.edu.wat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleDto implements Serializable {
    Date transferDate;
    String threadName;
    String randomContent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleDto simpleDto = (SimpleDto) o;
        return Objects.equals(transferDate, simpleDto.transferDate) &&
                Objects.equals(threadName, simpleDto.threadName) &&
                Objects.equals(randomContent, simpleDto.randomContent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(transferDate, threadName, randomContent);
    }
}
