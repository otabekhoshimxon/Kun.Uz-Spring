package uz.kun.repository;
//User :Lenovo
//Date :10.06.2022
//Time :18:22
//Project Name :lesson_14_kun_uz


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleEntity;
import uz.kun.enums.ArticleStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {


    @Modifying
    @Transactional
    @Query("update ArticleEntity  set visible=?2 where id=?1")
    int changeVisibleById(String id, boolean b);

    @Query("from ArticleEntity where id=?1")
    Optional<ArticleEntity> findById1(String id);

    @Query("from ArticleEntity  where status=?1")
    List<ArticleEntity> getPublished(ArticleStatus status);
    @Query("from ArticleEntity" )
    List<ArticleEntity> findAllForAdmin();


    @Query(value = "SELECT art.* " +
            " FROM article_type as a " +
            " inner join article as art on art.id = a.article_id " +
            " inner join type as t on t.id = a.types_id " +
            " Where  t.key =:key  " +
            " order by art.publish_date " +
            " limit 5",
            nativeQuery = true)
    List<ArticleEntity>  getLast(String key);
}
