package uz.kun.entity;
//User :Lenovo
//Date :21.06.2022
//Time :21:22
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "attach_profile")
public class AttachProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "profile_id")
    @ManyToOne
    private ProfileEntity profile;

    @JoinColumn(name = "attach_id")
    @ManyToOne
    private AttachEntity attach;


}
