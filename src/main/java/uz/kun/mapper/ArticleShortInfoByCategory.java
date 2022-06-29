package uz.kun.mapper;
//User :Lenovo
//Date :17.06.2022
//Time :18:02
//Project Name :Kun.uz

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


public interface ArticleShortInfoByCategory {

     String getid();
     String getTitle();
     String getDescription();
     LocalDateTime getPublish_date();


}
