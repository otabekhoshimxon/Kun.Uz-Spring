package uz.kun.service;
//User :Lenovo
//Date :13.06.2022
//Time :14:47
//Project Name :lesson_14_kun_uz

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.ArticleLikeEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.LikeStatus;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.ArticleLikeRepository;
import uz.kun.repository.ArticleRepository;

import java.util.Optional;


@Service
@Validated(ArticleLikeEntity.class)
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public void articleLike(String articleId, Integer pId) {
        likeDislike(articleId, pId, LikeStatus.LIKE);
    }

    public void articleDisLike(String articleId, Integer pId) {
        likeDislike(articleId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(String articleId, Integer pId, LikeStatus status) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        if (optional.isPresent()) {
            ArticleLikeEntity like = optional.get();
            like.setLikeStatus(status);
            articleLikeRepository.save(like);
            return;
        }
        boolean articleExists = articleRepository.existsById(articleId);
        if (!articleExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        ArticleLikeEntity like = new ArticleLikeEntity();
        like.setArticle(new ArticleEntity(articleId));
        like.setProfile(new ProfileEntity(pId));
        like.setLikeStatus(status);
        System.out.println(like);
        articleLikeRepository.save(like);
    }

    public void removeLike(String articleId, Integer pId) {
       /* Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleLikeRepository.delete(articleLikeEntity);
        });*/
        articleLikeRepository.delete(articleId, pId);
    }
}
