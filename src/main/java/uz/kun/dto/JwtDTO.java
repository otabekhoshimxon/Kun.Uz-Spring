package uz.kun.dto;
//User :Lenovo
//Date :15.06.2022
//Time :16:54
//Project Name :Kun.uz

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.kun.enums.ProfileRole;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class JwtDTO {

    private Integer id;
    private ProfileRole role;



}
