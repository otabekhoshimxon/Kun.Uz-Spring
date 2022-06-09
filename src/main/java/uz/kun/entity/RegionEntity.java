package uz.kun.entity;
//User :Lenovo
//Date :09.06.2022
//Time :5:02
//Project Name :Kun.uzWithThymleaf

import lombok.Data;
import uz.kun.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "region")
public class RegionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime created_date;
    @Column
    private String key;
    @Column
    private String name_uz;
    @Column
    private String name_ru;
    @Column
    private String name_en;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;




}
