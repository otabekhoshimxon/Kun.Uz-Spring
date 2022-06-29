package uz.kun.entity;
//User :Lenovo
//Date :17.06.2022
//Time :14:12
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "saved_article")
public class SavedArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private ProfileEntity profile;
    @ManyToOne
    private ArticleEntity article;



    private LocalDateTime createdDate=LocalDateTime.now();
    public SavedArticleEntity() {
    }

    public SavedArticleEntity(ProfileEntity profile, ArticleEntity article) {
        this.profile = profile;
        this.article = article;
    }
}
