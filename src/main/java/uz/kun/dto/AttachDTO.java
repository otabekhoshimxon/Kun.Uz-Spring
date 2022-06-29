package uz.kun.dto;
//User :Lenovo
//Date :22.06.2022
//Time :16:46
//Project Name :Kun.uz

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private String id;
    private String originalName;
    private String extension;
    private String url;
    private String path;
    private Long size;
    private LocalDateTime createdDate;
}


