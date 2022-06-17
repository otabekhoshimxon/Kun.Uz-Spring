package uz.kun.service;

import uz.kun.dto.ArticleCreateDTO;
import uz.kun.dto.ArticleDTO;
import uz.kun.entity.*;
import uz.kun.enums.ArticleStatus;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.repository.ProfileRepository;
import uz.kun.util.Convertor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
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
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleTagService articleTagService;
@Autowired
    private ProfileRepository profileRepository;

    public ArticleDTO create(ArticleCreateDTO dto, Integer profileId) {
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

        System.out.println(entity);
        articleTypeService.create(entity, dto.getTypesList()); // type

        articleTagService.create(entity, dto.getTagList());  // tag

        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

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

    public void update(String id, ArticleCreateDTO dto, Integer profileId) {


        Optional<ArticleEntity> byId = articleRepository.findById(id);
        if (byId.isEmpty())
        {
            throw new ItemNotFoundException("Article not found");

        }
        ArticleEntity entity = byId.get();

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
        entity.setPublishDate(null);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);


        articleRepository.save(entity);
        articleTypeService.create(entity, dto.getTypesList()); // type

        articleTagService.create(entity, dto.getTagList());  // tag

        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);

    }


    public void delete(String id) {
        Optional<ArticleEntity> byId = articleRepository.findById(id);
        if (byId.isPresent()) {

            if (byId.get().getVisible().equals(true))
            {
                articleRepository.changeVisibleById(id, false);
            }


        }

        //
    }


    public List<ArticleDTO> getAll() {
        List<ArticleDTO> articleDTOS = new LinkedList<>();
        List<ArticleEntity> all = articleRepository.findAllForAdmin();
        return getWrapArticleDTOS(all);


    }

    public void publish(String id, Integer profileId) {

        Optional<ArticleEntity> byId = articleRepository.findById(id);
        if (byId.isEmpty())
        {
            throw new ItemNotFoundException("Article not found");

        }
        ArticleEntity entity = byId.get();
        if (entity.getPublishDate()!=null)
        {
            throw new ItemNotFoundException("Article already published");
        }
        Optional<ProfileEntity> byId1 = profileRepository.findById(profileId);
        if (byId1.isPresent())
        {
            entity.setPublisher(byId1.get());
            entity.setPublishDate(LocalDateTime.now());
            entity.setStatus(ArticleStatus.PUBLISHED);
            articleRepository.save(entity);
        }


    }


    public List<ArticleDTO> getPublishedArticleList() {



        List<ArticleEntity> all = articleRepository.getPublished(ArticleStatus.PUBLISHED);
        return getWrapArticleDTOS(all);
    }

    private List<ArticleDTO> getWrapArticleDTOS( List<ArticleEntity> all) {

        List<ArticleDTO> articleDTOS=new ArrayList<>();
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

    public List<ArticleDTO> getLast(String key) {


        List<ArticleEntity> last = articleRepository.getLast( key);
        return getWrapArticleDTOS(last);

    }
}

