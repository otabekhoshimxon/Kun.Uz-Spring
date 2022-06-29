package uz.kun.dto;

import uz.kun.entity.CategoryEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.entity.RegionEntity;
import uz.kun.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;
    private String title;
    private String content;
    private String description;
    private Integer viewCount;
    private Integer sharedCount;
    private ArticleStatus status;
    private Boolean visible = Boolean.TRUE;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime publishDate=null;
    private ProfileDTO moderator;
    private ProfileDTO publisher;
    private RegionDto region;
    private CategoryDTO category;
    private ArticleLikeDTO like;


}
