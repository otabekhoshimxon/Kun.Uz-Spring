package uz.kun.dto;
//User :Lenovo
//Date :09.06.2022
//Time :4:54
//Project Name :Kun.uzWithThymleaf


import lombok.Data;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;

import java.time.LocalDateTime;

@Data

public class ProfileDTO {
    private Long id;
    private String name;
    private String surname;
    private String eMail;
    private String jwt;
    private ProfileStatus status=ProfileStatus.ACTIVE;
    private ProfileRole role=ProfileRole.USER;
    private boolean visible=Boolean.TRUE;
    private LocalDateTime created_date=LocalDateTime.now();



}
