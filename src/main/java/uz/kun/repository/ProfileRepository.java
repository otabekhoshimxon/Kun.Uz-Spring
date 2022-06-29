package uz.kun.repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.ProfileStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity, Integer> {

    List<ProfileEntity> findAllByVisible(Boolean b);

    Optional<ProfileEntity> findByEmail(String email);


    @Modifying
    @Transactional
    @Query("update ProfileEntity set status=?2 where phone=?1")
    int updateStatusByPhone(String phone, ProfileStatus active);

    Optional<ProfileEntity> findByPhone(String phone);
}
