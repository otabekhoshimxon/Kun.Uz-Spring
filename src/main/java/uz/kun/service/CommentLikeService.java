package uz.kun.service;
//User :Lenovo
//Date :13.06.2022
//Time :14:47
//Project Name :lesson_14_kun_uz

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.entity.*;
import uz.kun.enums.LikeStatus;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.ArticleLikeRepository;
import uz.kun.repository.ArticleRepository;
import uz.kun.repository.CommentLikeRepository;
import uz.kun.repository.CommentRepository;

import java.util.Optional;


@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentRepository commentRepository;

    public void commentLike(Integer commentId, Integer pId) {

        likeDislike(commentId, pId, LikeStatus.LIKE);
    }

    public void commentDisLike(Integer commentId, Integer pId) {

        likeDislike(commentId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(Integer commnentId ,Integer pId, LikeStatus status) {
        Optional<CommentLikeEntity> optional = commentLikeRepository.findExists(commnentId, pId);
        if (optional.isPresent()) {
            CommentLikeEntity like = optional.get();
            like.setLikeStatus(status);
            commentLikeRepository.save(like);
            return;
        }
        boolean commentExists = commentRepository.existsById(commnentId);
        if (!commentExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        CommentLikeEntity like = new CommentLikeEntity();
        like.setComment(new CommentEntity(commnentId));
        like.setProfile(new ProfileEntity(pId));
        like.setLikeStatus(status);
        commentLikeRepository.save(like);
    }

    public void removeLike(Integer commentId, Integer pId) {
       /* Optional<CommentLikeEntity> optional = commentLikeRepository.findExists(commentId, pId);
        optional.ifPresent(commentLikeEntity -> {
            commentLikeRepository.delete(commentLikeEntity);
        });*/
        commentLikeRepository.delete(commentId, pId);
    }
}
