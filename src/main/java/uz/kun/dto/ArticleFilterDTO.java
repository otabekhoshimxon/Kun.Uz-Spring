package uz.kun.dto;
//User :Lenovo
//Date :24.06.2022
//Time :19:08
//Project Name :Kun.uz

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uz.kun.enums.ArticleStatus;

import javax.persistence.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFilterDTO {


    private String id;
    private String title;
    private String description;
    private String regionId;
    private String categoryId;
    private String publishedDateFrom;
    private String publishedDateTo;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;


}
