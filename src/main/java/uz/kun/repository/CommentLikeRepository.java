package uz.kun.repository;
//User :Lenovo
//Date :15.06.2022
//Time :18:44
//Project Name :Kun.uz

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.ArticleLikeEntity;
import uz.kun.entity.CommentLikeEntity;
import uz.kun.entity.ProfileEntity;

import javax.transaction.Transactional;
import java.util.Optional;


public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity , Integer> {

    //Optional<CommentLikeEntity> findByArticleAndProfile(ArticleEntity article, ProfileEntity profile);

    @Query("FROM CommentLikeEntity a where  a.comment.id=:articleId and a.profile.id =:profileId")
    Optional<CommentLikeEntity > findExists(Integer articleId, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CommentLikeEntity a where  a.comment.id=:articleId and a.profile.id =:profileId")
    void delete(Integer articleId, Integer profileId);

}

