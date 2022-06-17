package uz.kun.entity;
//User :Lenovo
//Date :12.06.2022
//Time :5:31
//Project Name :lesson_14_kun_uz

import lombok.Data;
import uz.kun.enums.LikeStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDateTime createdDate=LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @Column
    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;
}
