package uz.kun.repository;

import uz.kun.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findByKey(String key);

    List<CategoryEntity> findAllByVisible(Boolean b);


}
