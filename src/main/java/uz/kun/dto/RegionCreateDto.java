package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionCreateDto {
    private String key;
    private String nameUz;
    private String nameRu;
    private String nameEn;

}
