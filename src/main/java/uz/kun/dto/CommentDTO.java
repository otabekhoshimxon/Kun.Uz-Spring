package uz.kun.dto;
//User :Lenovo
//Date :12.06.2022
//Time :23:25
//Project Name :lesson_14_kun_uz

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {

    private Integer id;
    private LocalDateTime created_date=LocalDateTime.now();
    private LocalDateTime updated_time;
    private String content;
    private boolean visible=Boolean.TRUE;
    private CommentDTO comment;
    private ArticleDTO article;
    private ProfileDTO profile;






}
