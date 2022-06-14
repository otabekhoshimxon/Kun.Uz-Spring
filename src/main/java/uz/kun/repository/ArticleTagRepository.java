package uz.kun.repository;


import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleTagEntity;

public interface ArticleTagRepository extends CrudRepository<ArticleTagEntity, Integer> {

/*
    Optional<ArticleTagEntity> findByKey(String key);

    List<ArticleTagEntity> findAllByVisible(Boolean b);
*/


}
