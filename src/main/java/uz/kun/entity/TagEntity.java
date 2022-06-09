package uz.kun.entity;
//User :Lenovo
//Date :09.06.2022
//Time :5:09
//Project Name :Kun.uzWithThymleaf

import lombok.Data;
import uz.kun.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tag")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime created_date;
    @Column
    private String name;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;


}
