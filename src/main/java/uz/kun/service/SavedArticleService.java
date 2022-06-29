package uz.kun.service;
//User :Lenovo
//Date :17.06.2022
//Time :14:09
//Project Name :Kun.uz

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.dto.SavedArticleDTO;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.entity.SavedArticleEntity;
import uz.kun.exps.BadRequestException;
import uz.kun.repository.ArticleRepository;
import uz.kun.repository.ProfileRepository;
import uz.kun.repository.SavedArticleRepository;
import uz.kun.util.Convertor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedArticleService {
    @Autowired
    private Convertor convertor;
   @Autowired
    private ArticleRepository repository;
   @Autowired
    private ProfileRepository profileRepository;


   @Autowired
   private SavedArticleRepository savedArticleRepository;
    public void create(Integer profileId, String articleId) {

        Optional<ArticleEntity> article = repository.findById(articleId);
        Optional<ProfileEntity> profile = profileRepository.findById(profileId);

        if (article.isEmpty())
        {
            throw new BadRequestException("Article not found");
        }
         if (profile.isEmpty())
        {
            throw new BadRequestException("Profile not found");

        }
        ProfileEntity profileEntity = profile.get();
        ArticleEntity articleEntity = article.get();
        savedArticleRepository.save(new SavedArticleEntity(profileEntity,articleEntity));

    }

    public void delete(Integer profileId, String articleId) {
        Optional<ArticleEntity> article = repository.findById(articleId);
        Optional<ProfileEntity> profile = profileRepository.findById(profileId);

        if (article.isEmpty())
        {
            throw new BadRequestException("Article not found");
        }
        if (profile.isEmpty())
        {
            throw new BadRequestException("Profile not found");

        }


        ProfileEntity profileEntity = profile.get();
        ArticleEntity articleEntity = article.get();
        savedArticleRepository.delete(new SavedArticleEntity(profileEntity,articleEntity));

    }

    public List<SavedArticleDTO> getAll(Integer profileId) {


        List<SavedArticleEntity> byProfileId = savedArticleRepository.findByProfileId(profileId);

        if (byProfileId.isEmpty())
        {
            throw new BadRequestException("Articles not found");
        }

        List<SavedArticleDTO> saved=new LinkedList<>();

        for (SavedArticleEntity savedArticleEntity : byProfileId) {

            SavedArticleDTO savedArticleDTO=new SavedArticleDTO();
            savedArticleDTO.setArticleDTO(convertor.entityToDTO(savedArticleEntity.getArticle()));
            saved.add(savedArticleDTO);
        }
        return saved;
    }
}
