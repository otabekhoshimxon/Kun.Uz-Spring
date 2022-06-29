package uz.kun.dto;
//User :Lenovo
//Date :22.06.2022
//Time :18:48
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VerificationDTO {

    @NotNull(message = "Code is required")
    @NotBlank(message = "Code is required")
    private String code;
    @NotNull(message = "Phone is required")
    @NotBlank(message = "Phone is required")
    private String phone;


}
