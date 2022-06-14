package uz.kun.repository;

import uz.kun.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    Optional<RegionEntity> findByKey(String key);

    List<RegionEntity> findAllByVisible(Boolean b);


}
