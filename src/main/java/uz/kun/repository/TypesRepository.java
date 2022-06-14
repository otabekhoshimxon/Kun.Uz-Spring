package uz.kun.repository;

import uz.kun.entity.TypesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TypesRepository extends  PagingAndSortingRepository<TypesEntity,Integer> {

    Optional<TypesEntity> findByKey(String key);

    List<TypesEntity> findAllByVisible(Boolean b);



    long countAllBy();
/*    List<ArticleTypeDTO> getPagination(int page, int size);*/
}
