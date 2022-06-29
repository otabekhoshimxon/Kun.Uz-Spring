package uz.kun.controller;
//User :Lenovo
//Date :24.06.2022
//Time :5:33
//Project Name :Kun.uz

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.enums.ProfileRole;
import uz.kun.service.SmsService;
import uz.kun.util.HttpHeaderUtil;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sms")
@Api(tags = "Sms controller ")
public class SmsController {


    @Autowired
    private SmsService smsService;


    @GetMapping("/get")
    public ResponseEntity<?> getSms(@RequestBody int page, @RequestBody int size, HttpServletRequest servletRequest)
    {
        HttpHeaderUtil.getId(servletRequest, ProfileRole.ADMIN);
        return ResponseEntity.ok(smsService.getSms(page,size));
    }



}
