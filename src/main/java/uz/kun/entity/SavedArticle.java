package uz.kun.entity;
//User :Lenovo
//Date :17.06.2022
//Time :14:12
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "saved_article")
public class SavedArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @ManyToOne
    private ProfileEntity profile;
    @Column
    @ManyToOne
    private ArticleEntity article;

    public SavedArticle() {
    }

    public SavedArticle(ProfileEntity profile, ArticleEntity article) {
        this.profile = profile;
        this.article = article;
    }
}
