package uz.kun.dto;
//User :Lenovo
//Date :19.06.2022
//Time :21:42
//Project Name :Kun.uz

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleByTypesAndRegionKeyRequestDTO {


    @NotBlank(message = "Types is required ")
    @NotNull(message = "Types is required ")
    private String types;

    @NotBlank(message = "Region Key is required ")
    @NotNull(message = "Region Key is required ")
    private String regionKey;


}
