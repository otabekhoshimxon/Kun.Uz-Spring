package uz.kun.dto;
//User :Lenovo
//Date :09.06.2022
//Time :5:09
//Project Name :Kun.uzWithThymleaf

import lombok.Data;
import uz.kun.enums.Status;

import java.time.LocalDateTime;

@Data
public class TagDTO {
    private Long id;
    private LocalDateTime created_date;
    private String name;
    private Status status;


}
