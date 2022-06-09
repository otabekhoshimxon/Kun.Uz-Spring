package uz.kun.dto;
//User :Lenovo
//Date :09.06.2022
//Time :5:06
//Project Name :Kun.uzWithThymleaf

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDTO {
    private Long id;
    private LocalDateTime created_date;
    private String key;
    private String name_uz;
    private String name_ru;
    private String name_en;


}
