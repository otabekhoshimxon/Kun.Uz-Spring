package uz.kun.entity;
//User :Lenovo
//Date :12.06.2022
//Time :5:18
//Project Name :lesson_14_kun_uz

import uz.kun.enums.LikeStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "article_like")
public class ArticleLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime createdDate;
    @Column
    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleEntity article;





}
