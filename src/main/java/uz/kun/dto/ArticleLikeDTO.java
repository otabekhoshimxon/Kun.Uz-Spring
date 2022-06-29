package uz.kun.dto;
//User :Lenovo
//Date :15.06.2022
//Time :19:20
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;

@Data
public class ArticleLikeDTO {

    private String articleId;

    private Integer likeCount=0;
    private Integer disLikeCount=0;



}
