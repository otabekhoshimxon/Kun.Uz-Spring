package uz.kun.dto;
//User :Lenovo
//Date :09.06.2022
//Time :19:53
//Project Name :Kun.uzWithThymleaf

import lombok.Data;

import javax.persistence.*;

@Data
public class AuthDTO {

    private String login;
    private String password;


}
