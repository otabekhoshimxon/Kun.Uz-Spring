package uz.kun.dto.integration;
//User :Lenovo
//Date :24.06.2022
//Time :16:41
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;

@Data
public class ResponseInfoDTO {

    private int status;
    private String message;

    public ResponseInfoDTO() {
    }

    public ResponseInfoDTO(int status) {
        this.status = status;
    }

    public ResponseInfoDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
