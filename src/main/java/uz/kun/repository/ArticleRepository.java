package uz.kun.repository;
//User :Lenovo
//Date :10.06.2022
//Time :18:22
//Project Name :lesson_14_kun_uz


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.CategoryEntity;
import uz.kun.enums.ArticleStatus;
import uz.kun.mapper.ArticleShortInfoByCategory;
import uz.kun.mapper.ArticleShortInfoByType;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {


    @Modifying
    @Transactional
    @Query("update ArticleEntity  set visible=?2 where id=?1")
    int changeVisibleById(String id, boolean b);


    //////////////////////////////////////////////////////////////////////////
    @Query("from ArticleEntity where id=?1")
    Optional<ArticleEntity> findById1(String id);
    //////////////////////////////////////////////////////////////////////////


    @Query("from ArticleEntity  where status=?1 and  visible=true ")
    List<ArticleEntity> getPublished(ArticleStatus status);


    //////////////////////////////////////////////////////////////////////////
    @Query("from ArticleEntity")
    List<ArticleEntity> findAllForAdmin();

    //////////////////////////////////////////////////////////////////////////


    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleTypeEntity as a " +
            " inner join a.article as art " +
            " inner join a.types as t " +
            " Where t.key =:typeKey and art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast5ByType(@Param("typeKey") String typeKey, Pageable pageable);

    //////////////////////////////////////////////////////////////////////////

    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleEntity as art " +
            " Where art.visible = true and art.status = 'PUBLISHED' and art.id not in (:idList) " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast8NotIn(@Param("idList") List<String> idList, Pageable pageable);
    //////////////////////////////////////////////////////////////////////////
    @Modifying
    @Transactional
    @Query("update ArticleEntity a set a.status ='PUBLISHED', a.publishDate =:time, a.publisher.id=:pid where a.id=:articleId")
    void changeStatusToPublish(@Param("articleId") String articleId, @Param("pid") Integer pId, @Param("time") LocalDateTime time);
    //////////////////////////////////////////////////////////////////////////
    @Modifying
    @Transactional
    @Query("update ArticleEntity a set a.status ='NOT_PUBLISHED' where a.id=:articleId")
    void changeStatusNotPublish(@Param("articleId") String articleId);

    //////////////////////////////////////////////////////////////////////////




    @Query(value = "select  art.id as id ,art.title as title, art.description as description,art.publish_date as publishDate" +
            "   from article as art " +
            "   inner join category as cat on art.category_id = cat.id " +
            " where cat.key=:key and art.status='PUBLISHED' and art.visible=true  " +
            " order by art.publish_date limit 5 ",
            nativeQuery = true)
    List<ArticleShortInfoByCategory> find5ArticleByCategoryKey(@Param("key") String key);
    //////////////////////////////////////////////////////////////////////////
    List<ArticleEntity> getTop4ArticleEntitiesByStatusOrderByViewCountDesc(ArticleStatus articleStatus);




    @Query(value = "select art.* from article art " +
            "inner join article_type t on art.id = t.article_id " +
            "inner join types t2 on t2.id = t.types_id"+
            " where t2.key=:key"+
            "  order by art.publish_date limit 5", nativeQuery = true)
    List<ArticleEntity> getLast5ByType1(@Param("key")String key);
    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleEntity  as art where art.category.key =:categoryKey and art.status =:status " +
            " and art.visible = true " +
            " order by art.publishDate ")
        //////////////////////////////////////////////////////////////////////////

    /*
        @Query("from ArticleEntity art inner join ArticleTypeEntity at " +
                "inner join TypesEntity ty " +
                "where ")
        Page<ArticleEntity> findLast5ByTypeAndLang(String key, Lang lang, Pageable pageable);*/
    List<ArticleEntity> findTop5ByCategoryAndStatusAndVisibleTrueOrderByCreatedDateDesc(CategoryEntity category,
                                                                                        ArticleStatus status);

    List<ArticleEntity> findTop5ByCategoryAndStatusAndVisibleOrderByCreatedDateDesc(CategoryEntity category,
                                                                                    ArticleStatus status, Boolean visible);
    Page<ArticleEntity> findLast5ByCategory(@Param("categoryKey") String categoryKey,
                                            @Param("status") ArticleStatus status, Pageable pageable);





    //native query
    @Query(value = "select a.* from article_tag at "+
            "inner join tag t on t.id = at.tag_id "+
            "inner join article a on at.article_id = a.id "+
            "where t.name=:key",nativeQuery = true)
    List<ArticleEntity> getTop4ArticleEntitiesByTagNameNative(@Param("key") String key);

//jpa query

      @Query(value = "select new ArticleEntity(art.id ,art.title,art.description,art.publishDate) from ArticleTagEntity at "+
            "inner join at.article art "+
            "inner join at.tag t "+
            "where t.name=:key")
    List<ArticleEntity> getTop4ArticleEntitiesByTagNameJPA(@Param("key") String key);



    @Query(value = "SELECT  art.id as id ,art.title as title, art.description as description,art.publish_date as publishDate " +
            " FROM article as art " +
            " inner join article_type as a on a.article_id = art.id " +
            " inner join types as t on t.id = a.types_id " +
            " where  t.key =:key and art.visible = true and art.status = 'PUBLISHED' and art.id not in (:id) " +
            " order by art.publish_date " +
            " limit 5 ",
            nativeQuery = true)
    List<ArticleShortInfoByType> getLast4ArticleByType(@Param("key") String key, @Param("id") String id);



    //native
    @Query(value = "select art.* from article art " +
            "inner join article_type a on art.id = a.article_id " +
            "inner join region r on art.region_id = r.id " +
            "inner join types t on t.id = a.types_id " +
            "where t.key=?1 and r.key=?2",nativeQuery = true)
    List<ArticleEntity> getLast5ArticleByTypesAndByRegionKeyNative(String types, String regionKey, Pageable pageable);


    //jpa query
    @Query(value = "select new ArticleEntity(art.id ,art.title,art.description,art.publishDate) from ArticleTypeEntity at " +
            "inner join at.article art " +
            "inner join at.article.region r  " +
            "inner join at.types t" +
            " where t.key=?1 and r.key=?2")
    List<ArticleEntity>  getLast5ArticleByTypesAndByRegionKeyJPA(String types, String regionKey, Pageable pageable);




    @Query("from ArticleEntity as art " +
            " where art.region.key=:key ")
    Page<ArticleEntity> getArticleListByRegionKey(@Param("key") String regionKey, Pageable pageable);


    @Query("from ArticleEntity as art " +
            " where art.category.key=?1 ")
    Page<ArticleEntity> getArticleListByCategoryKey(String regionKey, Pageable pageable);


/*    Optional<ArticleEntity> getArticleByIdAndByStatus(String id, ArticleStatus published);*/
}