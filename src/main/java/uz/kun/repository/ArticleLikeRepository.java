package uz.kun.repository;
//User :Lenovo
//Date :15.06.2022
//Time :18:44
//Project Name :Kun.uz

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.ArticleLikeEntity;
import uz.kun.entity.ProfileEntity;

import javax.transaction.Transactional;
import java.util.Optional;


public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

   // Optional<ArticleLikeEntity> findByArticleAndProfile(ArticleEntity article, ProfileEntity profile);

    @Query(value = "select * FROM article_like a where a.article_id =:a and a.profile_id =:b",nativeQuery = true)
    Optional<ArticleLikeEntity> findExists(@Param("a") String articleId, @Param("b")Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ArticleLikeEntity a where  a.article.id=:articleId and a.profile.id =:profileId")
    void delete(String articleId, Integer profileId);

}

