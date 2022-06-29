package uz.kun.dto.integration;
//User :Lenovo
//Date :22.06.2022
//Time :18:33
//Project Name :Kun.uz

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class SmsResponseDTO {

    private Boolean success;
    private String reason;
    private Integer resultCode;

}