package uz.kun.repository;
//User :Lenovo
//Date :10.06.2022
//Time :18:22
//Project Name :lesson_14_kun_uz


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.SavedArticleEntity;

import java.util.List;


public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer> {


    @Query("from SavedArticleEntity  where profile.id=?1")
    List<SavedArticleEntity> findByProfileId(Integer profileId);
}
