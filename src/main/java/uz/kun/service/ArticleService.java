package uz.kun.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import uz.kun.dto.*;
import uz.kun.entity.*;
import uz.kun.enums.ArticleStatus;
import uz.kun.enums.Lang;
import uz.kun.exps.BadRequestException;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.mapper.ArticleShortInfoByCategory;
import uz.kun.mapper.ArticleShortInfoByType;
import uz.kun.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.repository.CustomArticleRepository;
import uz.kun.repository.ProfileRepository;
import uz.kun.util.Convertor;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private Convertor convertor;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttachService service;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private CustomArticleRepository customArticleRepository;

    //*********************************Article create *************************************************//
    public ArticleDTO create(ArticleCreateDTO dto, Integer profileId, MultipartFile file) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());

        RegionEntity region = regionService.get(dto.getRegionId());
        entity.setRegion(region);

        CategoryEntity category = categoryService.get(dto.getCategoryId());
        entity.setCategory(category);

        ProfileEntity moderator = new ProfileEntity();
        moderator.setId(profileId);
        entity.setModerator(moderator);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);

        articleTypeService.create(entity, dto.getTypesList()); // type

        articleTagService.create(entity, dto.getTagList());  // tag

        AttachEntity attachEntity = service.saveArticleImageToSystem(file);
        entity.setImage(attachEntity);
        articleRepository.save(entity);
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setCategory(convertor.entityToDTO(entity.getCategory()));
        articleDTO.setContent(entity.getContent());
        articleDTO.setDescription(entity.getDescription());
        articleDTO.setModerator(convertor.entityToDTO(entity.getModerator()));
        articleDTO.setRegion(convertor.entityToDTO(entity.getRegion()));
        articleDTO.setStatus(entity.getStatus());
        articleDTO.setTitle(entity.getTitle());
        return articleDTO;
    }

    //*********************************Article update *************************************************//
    public void update(String id, ArticleUpdateDTO dto, Integer profileId) {

        ArticleEntity entity = get(id);
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);


        AttachEntity image = attachService.getById(dto.getImageId());
        String old = image.getId();

        if (entity.getImage()==null && dto.getImageId()!=null){

            entity.setImage(new AttachEntity(dto.getImageId()));

        } else if (entity.getImage()!=null && dto.getImageId()==null) {

            entity.setImage(null);
            articleRepository.save(entity);
            attachService.delete1(old);

        } else if (entity.getImage()!=null && dto.getImageId()!=null && !entity.getId().equals(dto.getImageId())) {

            entity.setImage(new AttachEntity(dto.getImageId()));
            attachService.delete1(entity.getId());

        }


        checkDto(entity,dto);
        articleRepository.save(entity);
        log.info("Article updated to -> {}",entity,dto);
    }

    public ArticleEntity get(String id){
        return articleRepository.findById(id).orElseThrow(()->{
            log.info("Article not found {}",id);
            throw new ItemNotFoundException("Article not found ");
        });
    }

    public void checkDto(ArticleEntity entity ,ArticleUpdateDTO dto) {

        if (dto.getRegionId()!=null){
            RegionEntity region = regionService.get(dto.getRegionId());
            entity.setRegion(region);
        }
        if (dto.getCategoryId()!=null){
            CategoryEntity category = categoryService.get(dto.getCategoryId());
            entity.setCategory(category);
        }

        if (dto.getTypesList()!=null){
            articleTypeService.create(entity, dto.getTypesList()); // type
        }
        if (dto.getTagList()!=null){
            articleTagService.create(entity, dto.getTagList());  // tag
        }


    }
    public void publish(String id, Integer profileId) {

        Optional<ArticleEntity> byId = articleRepository.findById(id);
        if (byId.isEmpty()) {
            log.error("Article not found");
            throw new ItemNotFoundException("Article not found");
        }
        ArticleEntity entity = byId.get();
        if (entity.getPublishDate() != null) {
            log.error("Article already published");
            throw new ItemNotFoundException("Article already published");
        }


        if (entity.getStatus().equals(ArticleStatus.NOT_PUBLISHED)) {

            Optional<ProfileEntity> byId1 = profileRepository.findById(profileId);
            if (byId1.isPresent()) {
                entity.setPublisher(byId1.get());
                entity.setPublishDate(LocalDateTime.now());
                entity.setStatus(ArticleStatus.PUBLISHED);
                articleRepository.changeStatusToPublish(byId.get().getId(), byId1.get().getId(), LocalDateTime.now());
            } else {

                articleRepository.changeStatusNotPublish(byId.get().getId());
            }

        }


    }

    public void delete(String id) {
        Optional<ArticleEntity> byId = articleRepository.findById(id);
        if (byId.isPresent()) {
            if (byId.get().getVisible().equals(true)) {
                log.info("Article deleted", byId.get());
                articleRepository.changeVisibleById(id, false);
            }
        }
    }


    public List<ArticleDTO> getAll() {
        List<ArticleEntity> all = articleRepository.findAllForAdmin();
        if (all.isEmpty()) {
            log.error("Articles not found");
            throw new ItemNotFoundException("Articles not found");
        }
        return getFullArticle(all);


    }


    public List<ArticleDTO> getPublishedArticleList() {
        List<ArticleEntity> all = articleRepository.getPublished(ArticleStatus.PUBLISHED);
        if (all.isEmpty()) {
            log.error("Articles not found");
            throw new ItemNotFoundException("Articles not found");
        }
        return getFullArticle(all);
    }

    public List<ArticleDTO> getLastByType(String key) {
        Pageable pageable = PageRequest.of(0, 5);
        return getArticleDTOS(key, pageable);

    }

    private List<ArticleDTO> getByMapperLast5ByType(List<ArticleShortInfoByType> last) {
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (ArticleShortInfoByType next : last) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(next.getid());
            articleDTO.setDescription(next.getDescription());
            articleDTO.setPublishDate(next.getPublishDate());
            articleDTO.setTitle(next.getTitle());
            articleDTOS.add(articleDTO);
        }
        return articleDTOS;

    }

    public List<ArticleDTO> getLastByCategoryKey(String key) {

/*
        Pageable pageable= PageRequest.of(0,5);*/
        List<ArticleShortInfoByCategory> top5ByArticleByCategory2 = articleRepository.find5ArticleByCategoryKey(key);
        List<ArticleDTO> list = new LinkedList<>();
        for (ArticleShortInfoByCategory articleEntity : top5ByArticleByCategory2) {

            list.add(convertor.entityToDTO(articleEntity));
        }
        return list;
    }

    public ArticleDTO getById(String id) {

        Optional<ArticleEntity> byId = articleRepository.findById(id);
        if (byId.isPresent()) {
            return convertor.entityToDTO(byId.get());
        }
        return null;
    }

    public List<ArticleDTO> getLastByTypeAndByLang(String key, Lang lang) {


/*
        Pageable pageable=PageRequest.of(0,5);
        Page<ArticleEntity> last = articleRepository.findLast5ByTypeAndLang(key ,lang,pageable);
        List<ArticleDTO> articleDTOS=new ArrayList<>();
        for (ArticleEntity art : last) {
            ArticleDTO articleDTO=new ArticleDTO();
            articleDTO.setPublishDate(art.getPublishDate());
            articleDTO.setId(art.getId());
            articleDTO.setDescription(art.getDescription());
            articleDTO.setTitle(art.getTitle());
            articleDTOS.add(articleDTO);
        }

        return articleDTOS;
*/

        return null;
    }

    public List<ArticleDTO> getLastByType(String key, Integer count) {

        Pageable pageable = PageRequest.of(0, count);
        return getArticleDTOS(key, pageable);

    }

    public List<ArticleDTO> getLast8NotIn(ArticleRequestIDsDTO ids) {
        Pageable pageable = PageRequest.of(0, 8);
        Page<ArticleEntity> last8NotIn = articleRepository.findLast8NotIn(ids.getIds(), pageable);
        return toShortArticle(last8NotIn);
    }


    public List<ArticleDTO> toShortArticle(List<ArticleEntity> p) {

        if (p.isEmpty()) {
            throw new ItemNotFoundException("Articles not found");
        }
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (ArticleEntity art : p) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setPublishDate(art.getPublishDate());
            articleDTO.setId(art.getId());
            articleDTO.setDescription(art.getDescription());
            articleDTO.setTitle(art.getTitle());
            articleDTOS.add(articleDTO);
        }
        return articleDTOS;
    }

    public List<ArticleDTO> toShortArticle(Page<ArticleEntity> p) {

        if (p.isEmpty()) {
            throw new ItemNotFoundException("Articles not found");
        }
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (ArticleEntity art : p) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setPublishDate(art.getPublishDate());
            articleDTO.setId(art.getId());
            articleDTO.setDescription(art.getDescription());
            articleDTO.setTitle(art.getTitle());
            articleDTOS.add(articleDTO);
        }
        return articleDTOS;
    }


    public List<ArticleDTO> get4MostReadArticles() {
        List<ArticleEntity> articleEntitiesByOrderByViewCountAsc = articleRepository.getTop4ArticleEntitiesByStatusOrderByViewCountDesc(ArticleStatus.PUBLISHED);
        if (articleEntitiesByOrderByViewCountAsc.isEmpty()) {
            throw new ItemNotFoundException("Articles not found");
        }
        return toShortArticle(articleEntitiesByOrderByViewCountAsc);
    }


    public List<ArticleDTO> getLast4ArticleByTagName(ArticleByTagNameRequestDTO key) {

        List<ArticleEntity> top4ArticleEntitiesByTagName = articleRepository.getTop4ArticleEntitiesByTagNameNative(key.getName());
        return toShortArticle(top4ArticleEntitiesByTagName);

    }

    public List<ArticleDTO> getLast5ArticleByTypesAndByRegionKey(ArticleByTypesAndRegionKeyRequestDTO typeAndKey) {

        Pageable pageable = PageRequest.of(0, 4);
        List<ArticleEntity> last5ArticleByTypesAndByRegionKey = articleRepository.getLast5ArticleByTypesAndByRegionKeyJPA(typeAndKey.getTypes(), typeAndKey.getRegionKey(), pageable);
        return toShortArticle(last5ArticleByTypesAndByRegionKey);

    }

    public List<ArticleDTO> getArticleListByRegionKey(String regionKey, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleEntity> articleListByRegionKey = articleRepository.getArticleListByRegionKey(regionKey, pageable);
        return toShortArticle(articleListByRegionKey);
    }


    public List<ArticleDTO> getArticleListByCategoryKey(String regionKey, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleEntity> articleListByRegionKey = articleRepository.getArticleListByCategoryKey(regionKey, pageable);
        return toShortArticle(articleListByRegionKey);
    }

    public List<ArticleDTO> get5ArticleByCategoryKey(String regionKey) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArticleEntity> articleListByRegionKey = articleRepository.getArticleListByRegionKey(regionKey, pageable);
        return toShortArticle(articleListByRegionKey);
    }

    ////////////////////////////private methods////////////////////////////////

    private List<ArticleDTO> getFullArticle(List<ArticleEntity> all) {
        if (all.isEmpty()) {
            throw new ItemNotFoundException("Articles not found");
        }
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (ArticleEntity next : all) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(next.getId());
            articleDTO.setCategory(convertor.entityToDTO(next.getCategory()));
            articleDTO.setContent(next.getContent());
            articleDTO.setDescription(next.getDescription());
            articleDTO.setModerator(convertor.entityToDTO(next.getModerator()));
            articleDTO.setRegion(convertor.entityToDTO(next.getRegion()));
            articleDTO.setStatus(next.getStatus());
            articleDTO.setTitle(next.getTitle());
            articleDTO.setPublisher(convertor.entityToDTO(next.getPublisher()));
            articleDTO.setPublishDate(next.getPublishDate());
            articleDTO.setViewCount(next.getViewCount());
            articleDTO.setSharedCount(next.getSharedCount());
            articleDTOS.add(articleDTO);
        }
        return articleDTOS;
    }

    private List<ArticleDTO> getArticleDTOS(String key, Pageable pageable) {
        Page<ArticleEntity> last = articleRepository.findLast5ByType(key, pageable);
        return toShortArticle(last);
    }

    private List<ArticleDTO> getFullArticle(Page<ArticleEntity> all) {

        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (ArticleEntity next : all) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(next.getId());
            articleDTO.setCategory(convertor.entityToDTO(next.getCategory()));
            articleDTO.setContent(next.getContent());
            articleDTO.setDescription(next.getDescription());
            articleDTO.setModerator(convertor.entityToDTO(next.getModerator()));
            articleDTO.setRegion(convertor.entityToDTO(next.getRegion()));
            articleDTO.setStatus(next.getStatus());
            articleDTO.setTitle(next.getTitle());
            articleDTO.setPublisher(convertor.entityToDTO(next.getPublisher()));
            articleDTO.setPublishDate(next.getPublishDate());
            articleDTO.setViewCount(next.getViewCount());
            articleDTO.setSharedCount(next.getSharedCount());
            articleDTOS.add(articleDTO);
        }
        return articleDTOS;
    }

    public void increaseArticleViewCount(String id) {

        Optional<ArticleEntity> byId = articleRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("Article not found");
        }
        byId.get().setViewCount(byId.get().getViewCount() + 1);
        articleRepository.save(byId.get());
    }

  /*  public void updateImage(String id, MultipartFile multipartFile) {

        Optional<ArticleEntity> byId = articleRepository.findById(id);
        if (byId.isEmpty()) {
            throw new BadRequestException("Article not found");
        }

        ArticleEntity articleEntity = byId.get();

        if (articleEntity.getImage() != null) {
            AttachEntity image = articleEntity.getImage();
            attachService.delete(image.getId() + "." + image.getExtention());
            articleEntity.setImage(attachService.saveArticleImageToSystem(multipartFile));
            articleRepository.save(articleEntity);
        }

        articleEntity.setImage(attachService.saveArticleImageToSystem(multipartFile));
        articleRepository.save(articleEntity);

    }*/



/*    public ArticleDTO getFullArticle(String id,Lang lang){

        Optional<ArticleEntity> articleByIdAndByStatus = articleRepository.getArticleByIdAndByStatus(id, ArticleStatus.PUBLISHED);
        if (articleByIdAndByStatus.isEmpty())
        {
            throw new ItemNotFoundException("Article not found");
        }
        ArticleEntity articleEntity = articleByIdAndByStatus.get();


        ArticleDTO fullAricle=new ArticleDTO();
        fullAricle.setId(articleEntity.getId());
        fullAricle.setCategory(convertor.entityToDTO(articleEntity.getCategory()));
        fullAricle.setContent(articleEntity.getContent());
        fullAricle.setDescription(articleEntity.getDescription());
        fullAricle.setModerator(convertor.entityToDTO(articleEntity.getModerator()));
        fullAricle.setRegion(convertor.entityToDTO(articleEntity.getRegion()));
        fullAricle.setStatus(articleEntity.getStatus());
        fullAricle.setTitle(articleEntity.getTitle());
        fullAricle.setPublisher(convertor.entityToDTO(articleEntity.getPublisher()));
        fullAricle.setPublishDate(articleEntity.getPublishDate());
        fullAricle.setViewCount(articleEntity.getViewCount());
        fullAricle.setSharedCount(articleEntity.getSharedCount());

        return fullAricle;

    }*/


    public List<ArticleDTO> filter(ArticleFilterDTO filterDTO) {
        List<ArticleEntity> filter = customArticleRepository.filter(filterDTO);
        List<ArticleDTO> list = new ArrayList<>();
        filter.forEach(articleEntity -> {
            list.add(convertor.entityToDTO(articleEntity));
        });
        return list;
    }

}

