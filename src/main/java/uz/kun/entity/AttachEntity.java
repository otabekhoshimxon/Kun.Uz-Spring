package uz.kun.entity;
//User :Lenovo
//Date :21.06.2022
//Time :5:15
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attach")
public class AttachEntity {

    @Id
    private String id;
    @Column
    private String originName;
    @Column
    private String extention;
    @Column
    private Long size;
    @Column
    private String path;

    @Column
    private LocalDateTime createdDate = LocalDateTime.now();


    public AttachEntity() {

    }

    public AttachEntity(String id, String originName, String extention, Long size, String path) {
        this.id = id;
        this.originName = originName;
        this.extention = extention;
        this.size = size;
        this.path = path;
    }

    public AttachEntity(String imageId) {
        this.id=imageId;
    }
}
