package uz.kun.dto.integration;
//User :Lenovo
//Date :22.06.2022
//Time :17:53
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;

@Data
public class SmsRequestDTO {

    private String key;
    private String phone;
    private String message;


}
