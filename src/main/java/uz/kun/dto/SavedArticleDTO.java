package uz.kun.dto;
//User :Lenovo
//Date :17.06.2022
//Time :14:26
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class SavedArticleDTO {


    private ArticleDTO articleDTO;
    private LocalDateTime createdDate=LocalDateTime.now();


}
