package uz.kun.service;

import uz.kun.entity.*;
import uz.kun.repository.ArticleTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagService {
    @Autowired
    private ArticleTagRepository articleTagRepository;
    @Autowired
    private TagService tagService;


    public void create(ArticleEntity article, List<String> tagList) {
        // ["#maymunChechak","#kasallik","#epidemiya"]
        for (String tagName : tagList) {
            TagEntity tag = tagService.createIfNotExists(tagName);

            ArticleTagEntity articleTagEntity = new ArticleTagEntity();
            articleTagEntity.setArticle(article);
            articleTagEntity.setTag(tag);

            articleTagRepository.save(articleTagEntity);

        }

    }
}
