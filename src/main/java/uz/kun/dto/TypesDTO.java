package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypesDTO {
    @NotNull(message = "Key is required")
    @NotBlank(message = "Key is required")
    private String key;
    @NotNull(message = "Name UZ is required")
    @NotBlank(message = "Name UZ is required")
    private String nameUz;
    @NotNull(message = "Name RU is required")
    @NotBlank(message = "Name RU is required")
    private String nameRu;
    @NotNull(message = "Name EN is required")
    @NotBlank(message = "Name EN is required")
    private String nameEn;
    private String name;
}
