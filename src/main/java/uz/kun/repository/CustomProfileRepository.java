package uz.kun.repository;
//User :Lenovo
//Date :25.06.2022
//Time :21:22
//Project Name :Kun.uz

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.ProfileFilterDTO;
import uz.kun.entity.ProfileEntity;

import javax.persistence.*;
import java.util.List;

@Repository
public class CustomProfileRepository {


    @Autowired
    private EntityManager entityManager;


    public List<ProfileEntity> filter (ProfileFilterDTO dto){
        StringBuilder query=new StringBuilder();
        query.append("select p from ProfileEntity p ");
        query.append("where p.status='ACTIVE' ");
        if (dto.getId()!=null){
            query.append(" and p.id= '"+dto.getId()+"' ");
        }
        if (dto.getName()!=null){
            query.append(" and p.email like '%"+ dto.getEmail() + "%' ");
        }
        if (dto.getRole()!=null){
            query.append(" and p.role= '"+dto.getRole()+"' ");
        }

        Query query1 = entityManager.createQuery(String.valueOf(query));
        List<ProfileEntity> resultList = query1.getResultList();
        System.err.println(resultList);

        return resultList ;

    }


}
