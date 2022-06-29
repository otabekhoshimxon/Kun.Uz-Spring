package uz.kun.dto;
//User :Lenovo
//Date :19.06.2022
//Time :21:42
//Project Name :Kun.uz

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ArticleByTagNameRequestDTO {


    @NotBlank(message = "Tag name is required ")
    private String name;


}
