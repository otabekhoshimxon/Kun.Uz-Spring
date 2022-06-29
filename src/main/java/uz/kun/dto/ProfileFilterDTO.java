package uz.kun.dto;
//User :Lenovo
//Date :25.06.2022
//Time :21:25
//Project Name :Kun.uz

import lombok.Data;
import uz.kun.enums.ProfileRole;

import javax.persistence.*;

@Data
public class ProfileFilterDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private ProfileRole role;


}
