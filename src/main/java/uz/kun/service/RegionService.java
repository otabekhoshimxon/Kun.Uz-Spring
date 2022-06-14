package uz.kun.service;

import uz.kun.dto.RegionDto;
import uz.kun.entity.RegionEntity;
import uz.kun.exps.AlreadyExist;
import uz.kun.exps.BadRequestException;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public void create(RegionDto regionDto) {

        Optional<RegionEntity> region = regionRepository.findByKey(regionDto.getKey());

        if (region.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(regionDto);

        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setKey(regionDto.getKey());
        regionEntity.setNameUz(regionDto.getNameUz());
        regionEntity.setNameRu(regionDto.getNameRu());
        regionEntity.setNameEn(regionDto.getNameEn());

        regionRepository.save(regionEntity);
    }

    private void isValid(RegionDto dto) {
        if (dto.getKey().length() < 5) {
            throw new BadRequestException("key to short");
        }

        if (dto.getNameUz() == null || dto.getNameUz().length() < 3) {
            throw new BadRequestException("wrong name uz");
        }

        if (dto.getNameRu() == null || dto.getNameRu().length() < 3) {
            throw new BadRequestException("wrong name ru");
        }

        if (dto.getNameEn() == null || dto.getNameEn().length() < 3) {
            throw new BadRequestException("wrong name en");
        }
    }

    public List<RegionDto> getList() {

        Iterable<RegionEntity> all = regionRepository.findAllByVisible(true);
        List<RegionDto> dtoList = new LinkedList<>();

        all.forEach(regionEntity -> {
            RegionDto dto = new RegionDto();
            dto.setKey(regionEntity.getKey());
            dto.setNameUz(regionEntity.getNameUz());
            dto.setNameRu(regionEntity.getNameRu());
            dto.setNameEn(regionEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Region not found");
        });
    }

    public List<RegionDto> getListOnlyForAdmin() {

        Iterable<RegionEntity> all = regionRepository.findAll();
        List<RegionDto> dtoList = new LinkedList<>();

        all.forEach(regionEntity -> {
            RegionDto dto = new RegionDto();
            dto.setKey(regionEntity.getKey());
            dto.setNameUz(regionEntity.getNameUz());
            dto.setNameRu(regionEntity.getNameRu());
            dto.setNameEn(regionEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public void update(Integer id, RegionDto dto) {
        Optional<RegionEntity> regionEntity = regionRepository.findById(id);

        if (regionEntity.isEmpty()) {
            throw new ItemNotFoundException("not found region");
        }

        if (regionEntity.get().getVisible().equals(Boolean.FALSE)) {
            throw new BadRequestException("is visible false");
        }

        RegionEntity entity = regionEntity.get();

        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity);
    }

    public void delete(Integer id) {

        Optional<RegionEntity> regionEntity = regionRepository.findById(id);

        if (regionEntity.isEmpty()) {
            throw new ItemNotFoundException("not found region");
        }

        if (regionEntity.get().getVisible().equals(Boolean.FALSE)) {
            throw new AlreadyExist("this region already visible false");
        }

        RegionEntity region = regionEntity.get();

        region.setVisible(Boolean.FALSE);

        regionRepository.save(region);
    }
}
