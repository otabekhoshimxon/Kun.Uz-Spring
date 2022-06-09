package uz.kun.entity;
//User :Lenovo
//Date :09.06.2022
//Time :4:54
//Project Name :Kun.uzWithThymleaf


import lombok.Data;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String eMail;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status=ProfileStatus.ACTIVE;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role=ProfileRole.USER;

    @Column
    private boolean visible=Boolean.TRUE;
    @Column
    private LocalDateTime created_date;



}
