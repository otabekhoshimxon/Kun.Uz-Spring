package uz.kun.repository;
//User :Lenovo
//Date :21.06.2022
//Time :5:19
//Project Name :Kun.uz

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.kun.entity.AttachEntity;

import javax.persistence.*;


public interface AttachRepository extends PagingAndSortingRepository<AttachEntity, String> {



    @Query("from AttachEntity ")
    Page<AttachEntity> getAll(Pageable pageable);


}
