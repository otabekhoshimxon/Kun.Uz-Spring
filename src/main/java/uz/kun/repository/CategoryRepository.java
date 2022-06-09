package uz.kun.repository;
//User :Lenovo
//Date :09.06.2022
//Time :5:06
//Project Name :Kun.uzWithThymleaf

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.CategoryEntity;

import java.time.LocalDateTime;


public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> , CrudRepository<CategoryEntity,Long> {



}
