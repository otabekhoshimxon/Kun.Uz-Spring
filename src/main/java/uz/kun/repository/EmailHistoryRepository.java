package uz.kun.repository;
//User :Lenovo
//Date :24.06.2022
//Time :16:49
//Project Name :Kun.uz

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.kun.entity.EmailHistoryEntity;

import javax.persistence.*;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer> {


    Long countByEmail(String email);


    @Query("from EmailHistoryEntity ")
    Page<EmailHistoryEntity> getAll(Pageable pageable);
}
