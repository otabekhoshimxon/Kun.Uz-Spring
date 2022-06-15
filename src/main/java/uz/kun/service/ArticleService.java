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

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
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
        articleDTO.setCategory(entity.getCategory());
        articleDTO.setContent(entity.getContent());
        articleDTO.setDescription(entity.getDescription());
        articleDTO.setModerator(entity.getModerator());
        articleDTO.setRegion(entity.getRegion());
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
        Iterable<ArticleEntity> all = articleRepository.findAll();
        while (all.iterator().hasNext()) {
            ArticleEntity next = all.iterator().next();
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(next.getId());
            articleDTO.setCategory(next.getCategory());
            articleDTO.setContent(next.getContent());
            articleDTO.setDescription(next.getDescription());
            articleDTO.setModerator(next.getModerator());
            articleDTO.setRegion(next.getRegion());
            articleDTO.setStatus(next.getStatus());
            articleDTO.setTitle(next.getTitle());
            articleDTO.setPublisher(next.getPublisher());
            articleDTO.setPublishDate(next.getPublishDate());
            articleDTO.setViewCount(next.getViewCount());
            articleDTO.setSharedCount(next.getSharedCount());
            articleDTOS.add(articleDTO);
        }
            return articleDTOS;


    }

    public void publish(String id, Integer profileId) {

        Optional<ArticleEntity> byId = articleRepository.findById(id);
        if (byId.isEmpty())
        {
            throw new ItemNotFoundException("Article not found");

        }
        ArticleEntity entity = byId.get();
        if (entity.getPublishDate()==null)
        {
            throw new ItemNotFoundException("Article already published");
        }
        Optional<ProfileEntity> byId1 = profileRepository.findById(profileId);
        if (byId1.isPresent())
        {
            entity.setPublisher(byId1.get());
            entity.setPublishDate(LocalDateTime.now());
            articleRepository.save(entity);
        }


    }
}

