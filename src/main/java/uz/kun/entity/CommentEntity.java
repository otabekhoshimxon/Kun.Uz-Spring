package uz.kun.entity;
//User :Lenovo
//Date :12.06.2022
//Time :5:25
//Project Name :lesson_14_kun_uz

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDateTime createdDate=LocalDateTime.now();
    @Column
    private String  content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleEntity article;
    @Column
    private Boolean  visible=Boolean.TRUE;
    @Column
    private LocalDateTime updatedDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_reply_id")
    private CommentEntity comment;

 @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;



}
