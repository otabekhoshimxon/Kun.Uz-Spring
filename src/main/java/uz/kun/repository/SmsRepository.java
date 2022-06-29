package uz.kun.repository;
//User :Lenovo
//Date :22.06.2022
//Time :17:45
//Project Name :Kun.uz

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import uz.kun.entity.SmsEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


public interface SmsRepository extends PagingAndSortingRepository<SmsEntity, Integer> {


    Optional<SmsEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    Page<SmsEntity> findAllByStatusTrue(Pageable pageable);

    @Query(value = "select count(*) from sms where phone =:phone and created_date > now() - INTERVAL '1 MINUTE' ",
            nativeQuery = true)
    Long getSmsCount(@Param("phone") String phone);

}
