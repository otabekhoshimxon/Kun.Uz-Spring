package uz.kun.repository;
//User :Lenovo
//Date :09.06.2022
//Time :5:06
//Project Name :Kun.uzWithThymleaf

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uz.kun.dto.ArticleTypeDTO;
import uz.kun.entity.ArticleTypeEntity;


public interface ArticleTypeRepository extends
        CrudRepository<ArticleTypeEntity,Long> ,
        JpaRepository<ArticleTypeEntity,Long>
{




}
