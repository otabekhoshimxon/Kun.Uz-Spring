package uz.kun.service;

import uz.kun.dto.TypesDTO;
import uz.kun.entity.TypesEntity;
import uz.kun.enums.Lang;
import uz.kun.exps.AlreadyExist;
import uz.kun.exps.BadRequestException;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TypesService {

    @Autowired
    private TypesRepository typesRepository;

    public void create(TypesDTO typesDto) {

        Optional<TypesEntity> articleTypeEntity = typesRepository.findByKey(typesDto.getKey());

        if (articleTypeEntity.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(typesDto);


        TypesEntity entity = new TypesEntity();
        entity.setKey(typesDto.getKey());
        entity.setNameUz(typesDto.getNameUz());
        entity.setNameRu(typesDto.getNameRu());
        entity.setNameEn(typesDto.getNameEn());

        typesRepository.save(entity);
    }

    private void isValid(TypesDTO dto) {
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

    public List<TypesDTO> getList() {

        Iterable<TypesEntity> all = typesRepository.findAllByVisible(true);
        List<TypesDTO> dtoList = new LinkedList<>();

        all.forEach(typesEntity -> {
            TypesDTO dto = new TypesDTO();
            dto.setKey(typesEntity.getKey());
            dto.setNameUz(typesEntity.getNameUz());
            dto.setNameRu(typesEntity.getNameRu());
            dto.setNameEn(typesEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public List<TypesDTO> getListOnlyForAdmin() {

        List<TypesEntity> all = typesRepository.findAllByVisible(true);

        List<TypesDTO> dtoList = new LinkedList<>();

        for (TypesEntity typesEntity : all) {

            TypesDTO dto = new TypesDTO();
            dto.setKey(typesEntity.getKey());
            dto.setNameUz(typesEntity.getNameUz());
            dto.setNameRu(typesEntity.getNameRu());
            dto.setNameEn(typesEntity.getNameEn());
            dtoList.add(dto);

        }
        return dtoList;
    }

    public void update(Integer id, TypesEntity dto) {
        Optional<TypesEntity> articleTypeEntity = typesRepository.findById(id);

        if (articleTypeEntity.isEmpty()) {
            throw new ItemNotFoundException("not found articleType");
        }

        TypesEntity entity = articleTypeEntity.get();


        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        typesRepository.save(entity);
    }

    public void delete(Integer id) {

        Optional<TypesEntity> entity = typesRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ItemNotFoundException("not found articleType");
        }

        if (entity.get().getVisible().equals(Boolean.FALSE)) {
            throw new AlreadyExist("this articleType already visible false");
        }

        TypesEntity articleType = entity.get();

        articleType.setVisible(Boolean.FALSE);

        typesRepository.save(articleType);
    }

    public List<TypesDTO> getListByLang(Lang lang) {

        List<TypesEntity> all = typesRepository.findAllByVisible(true);
        List<TypesDTO> dtoList = new LinkedList<>();


        for (TypesEntity typesEntity : all) {
            TypesDTO dto = new TypesDTO();
            dto.setKey(typesEntity.getKey());

            switch (lang) {
                case ru -> {
                    dto.setName(typesEntity.getNameRu());
                    break;
                }
                case en -> {
                    dto.setName(typesEntity.getNameEn());
                    break;
                }
                case uz -> {
                    dto.setName(typesEntity.getNameUz());
                    break;
                }
                default -> dto.setName(typesEntity.getNameUz());
            }
            dtoList.add(dto);
        }
        return dtoList;


    }

    public List<TypesDTO> getPage(int page, int size) {

        List<TypesEntity> list=typesRepository.pagination(page,size);
        long l = typesRepository.countAllBy();


        List<TypesDTO> dtoList = new LinkedList<>();
        for (TypesEntity typesEntity : list) {

            TypesDTO dto = new TypesDTO();
            dto.setKey(typesEntity.getKey());
            dto.setNameUz(typesEntity.getNameUz());
            dto.setNameRu(typesEntity.getNameRu());
            dto.setNameEn(typesEntity.getNameEn());
            dtoList.add(dto);

        }
        return dtoList;


    }
    public List<TypesDTO> sortByName() {


        Sort sort=Sort.by(Sort.Direction.DESC,"id");


        Iterable<TypesEntity> all = typesRepository.findAll(sort);

        Iterator<TypesEntity> iterator = all.iterator();



        return null;
    }
}
