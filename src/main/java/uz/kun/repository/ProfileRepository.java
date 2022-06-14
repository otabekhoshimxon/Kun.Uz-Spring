package uz.kun.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.kun.entity.ProfileEntity;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity, Integer> {

    List<ProfileEntity> findAllByVisible(Boolean b);

    Optional<ProfileEntity> findByEmail(String email);
}
