package uz.kun.dto;
//User :Lenovo
//Date :19.06.2022
//Time :21:42
//Project Name :Kun.uz

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ArticleRequestIDsDTO {

    @NotNull(message = "Id's required ")
    private List<String> ids;


}
