package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Integer id;
    private String key;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Boolean visible = Boolean.TRUE;
    private LocalDateTime createdDate = LocalDateTime.now();

}
