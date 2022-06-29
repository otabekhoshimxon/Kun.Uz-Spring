package uz.kun.util;//User :Lenovo
//Date :09.06.2022
//Time :6:36
//Project Name :Kun.uzWithThymleaf

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import uz.kun.dto.*;
import uz.kun.entity.*;
import uz.kun.mapper.ArticleShortInfoByCategory;

@Component
public  class Convertor{




    public static ProfileDTO entityToDTO(ProfileEntity entity)
    {
        ProfileDTO dto=new ProfileDTO();
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

    public  ProfileEntity DTOToEntity(ProfileDTO dto) {
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
    }       public ArticleDTO  entityToDTO(ArticleShortInfoByCategory dto) {
        ArticleDTO entity=new ArticleDTO();
        entity.setPublishDate(dto.getPublish_date());
        entity.setId(dto.getid());
        entity.setTitle(dto.getTitle());
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


    public ArticleDTO entityToDTO(ArticleEntity article) {

        ArticleDTO dto=new ArticleDTO();
        dto.setPublishDate(article.getPublishDate());
        dto.setId(article.getId());
        dto.setCreatedDate(article.getCreatedDate());
        dto.setPublisher(entityToDTO(article.getPublisher()));
        dto.setModerator(entityToDTO(article.getModerator()));
        dto.setCategory(entityToDTO(article.getCategory()));
        dto.setContent(article.getContent());
        dto.setRegion(entityToDTO(article.getRegion()));
        dto.setVisible(article.getVisible());
        dto.setStatus(article.getStatus());
        dto.setSharedCount(article.getSharedCount());
        dto.setTitle(article.getTitle());
        dto.setViewCount(article.getViewCount());
        dto.setDescription(article.getDescription());
        return dto;
    }

    public SmsDTO entityToDTO(SmsEntity smsEntity) {
        SmsDTO smsDTO=new SmsDTO();
        smsDTO.setCode(smsEntity.getCode());
        smsDTO.setPhone(smsEntity.getPhone());
        smsDTO.setStatus(smsEntity.isStatus());
        smsDTO.setCreatedDate(smsEntity.getCreatedDate());
        smsDTO.setId(smsDTO.getId());
        return smsDTO;

    }
}
