package uz.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uz.kun.enums.ProfileRole;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surName;
    private String email;
    private ProfileRole role;
    private String password;

    private String jwt;

}
