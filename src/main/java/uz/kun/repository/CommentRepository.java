package uz.kun.repository;
//User :Lenovo
//Date :12.06.2022
//Time :23:21
//Project Name :lesson_14_kun_uz


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.kun.entity.CommentEntity;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {


    @Query("from CommentEntity where profile.id=?1 and visible=true ")
    List<CommentEntity> getCommentsByProfileId(Integer pID);
    Optional<CommentEntity> findByProfileIdAndId(Integer pID, Integer id);

 /*   @Query("from CommentEntity where profile.id=?1 and id=?2")
    Optional<CommentEntity> existsByIdAndProfileId(Integer pID, Integer id);*/
}
