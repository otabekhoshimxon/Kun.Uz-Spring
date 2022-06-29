package uz.kun.dto;
//User :Lenovo
//Date :24.06.2022
//Time :5:46
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class SmsDTO {
    @NotNull(message = "Id is required")
    private Integer id;
    @NotNull(message = "Phone is required")
    @NotBlank(message = "Phone is required")
    private String phone;
    @NotNull(message = "Code is required")
    @NotBlank(message = "Code is required")
    private String code;
    private LocalDateTime createdDate;

    private boolean status;
}
