package uz.kun.repository;
//User :Lenovo
//Date :10.06.2022
//Time :18:22
//Project Name :lesson_14_kun_uz


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.SavedArticle;
import uz.kun.enums.ArticleStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface SavedArticleRepository extends CrudRepository<SavedArticle, Integer> {


    @Query("from SavedArticle  where profile.id=?1")
    List<SavedArticle> findByProfileId(Integer profileId);
}
