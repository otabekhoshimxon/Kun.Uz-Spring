package uz.kun.repository;
//User :Lenovo
//Date :09.06.2022
//Time :5:06
//Project Name :Kun.uzWithThymleaf

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.RegionEntity;
import uz.kun.entity.TagEntity;


public interface TagRepository extends JpaRepository<TagEntity,Long> , CrudRepository<TagEntity,Long> {



}
