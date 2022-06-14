package uz.kun.service;

import uz.kun.entity.ArticleEntity;
import uz.kun.entity.ArticleTypeEntity;
import uz.kun.entity.TypesEntity;
import uz.kun.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public void create(ArticleEntity article, List<Integer> typesList) {
        for (Integer typesId : typesList) {
            ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
            articleTypeEntity.setArticle(article);
            articleTypeEntity.setTypes(new TypesEntity(typesId));
            articleTypeRepository.save(articleTypeEntity);
        }
    }

}
