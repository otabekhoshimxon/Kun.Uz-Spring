package uz.kun.controller;
//User :Lenovo
//Date :09.06.2022
//Time :5:14
//Project Name :Kun.uzWithThymleaf

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.AuthDTO;
import uz.kun.entity.AuthServise;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServise authServise;








}
