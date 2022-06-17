package uz.kun.util;//User :Lenovo
//Date :09.06.2022
//Time :6:36
//Project Name :Kun.uzWithThymleaf

import org.springframework.stereotype.Component;
import uz.kun.dto.ArticleDTO;
import uz.kun.dto.CategoryDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.RegionDto;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.CategoryEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.entity.RegionEntity;

@Component
public  class Convertor{




    public ProfileDTO entityToDTO(ProfileEntity entity)
    {
        ProfileDTO dto=new ProfileDTO();
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setId(entity.getId());
        return  dto;

    }

    public CategoryDTO entityToDTO(CategoryEntity entity)
    {
        CategoryDTO dto=new CategoryDTO();
        dto.setKey(entity.getKey());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setNameUz(entity.getNameUz());
        dto.setVisible(entity.getVisible());
        return  dto;

    }

    public ProfileEntity DTOToEntity(ProfileDTO dto) {
        ProfileEntity entity=new ProfileEntity();
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setId(dto.getId());
        return entity;
    }


        public CategoryEntity DTOToEntity(CategoryDTO dto) {
        CategoryEntity entity=new CategoryEntity();
        entity.setKey(dto.getKey());
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        entity.setVisible(dto.getVisible());
        return entity;
    }



       public ArticleEntity  DTOToEntity(ArticleDTO dto) {
        ArticleEntity entity=new ArticleEntity();
        entity.setPublishDate(dto.getPublishDate());
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setPublisher(DTOToEntity(dto.getPublisher()));
        entity.setModerator(DTOToEntity(dto.getModerator()));
        entity.setCategory(DTOToEntity(dto.getCategory()));
        entity.setContent(dto.getContent());
        entity.setRegion(DTOToEntity(dto.getRegion()));
        entity.setVisible(dto.getVisible());
        entity.setStatus(dto.getStatus());
        entity.setSharedCount(dto.getSharedCount());
        entity.setTitle(dto.getTitle());
        entity.setViewCount(dto.getViewCount());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    private RegionEntity DTOToEntity(RegionDto region) {

        RegionEntity regionEntity=new RegionEntity();
        regionEntity.setNameUz(region.getNameUz());
        regionEntity.setNameRu(region.getNameRu());
        regionEntity.setNameEn(region.getNameEn());
        regionEntity.setKey(region.getKey());
        regionEntity.setId(region.getId());
        return regionEntity;

    }public RegionDto entityToDTO(RegionEntity entity) {

        RegionDto dto=new RegionDto();
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setKey(entity.getKey());
        dto.setId(entity.getId());
        return dto;

    }


}
