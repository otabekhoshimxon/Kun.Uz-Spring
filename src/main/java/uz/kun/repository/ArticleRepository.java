package uz.kun.repository;
//User :Lenovo
//Date :10.06.2022
//Time :18:22
//Project Name :lesson_14_kun_uz


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleEntity;

import javax.transaction.Transactional;
import java.util.Optional;


public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {


    @Modifying
    @Transactional
    @Query("update ArticleEntity  set visible=?2 where id=?1")
    int changeVisibleById(String id, boolean b);

    @Query("from ArticleEntity where id=?1")
    Optional<ArticleEntity> findById1(String id);
}
