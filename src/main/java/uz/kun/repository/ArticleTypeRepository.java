package uz.kun.repository;


import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.ArticleTypeEntity;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {

/*
    Optional<ArticleTypeEntity> findByKey(String key);

    List<ArticleTypeEntity> findAllByVisible(Boolean b);
*/


}
