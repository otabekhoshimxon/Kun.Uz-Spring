package uz.kun.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class ArticleUpdateDTO {

    @NotNull(message = "Article title required ")
    @NotBlank(message = "Article title required ")
    private String title;
    @NotNull(message = "Article content required ")
    @NotBlank(message = "Article title required ")
    private String content;
    @NotNull(message = "Article description required ")
    @NotBlank(message = "Article title required ")
    private String description;

    private String imageId;
    private Integer regionId;
    private Integer categoryId;
    private List<Integer> typesList;
    private List<String> tagList;
}
