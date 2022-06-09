package uz.kun.controller;
//User :Lenovo
//Date :09.06.2022
//Time :5:14
//Project Name :Kun.uzWithThymleaf

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.ProfileDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ProfileServise;
import uz.kun.util.JWTUtil;


@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileServise profileServise;



    @PostMapping("/create")
    public ResponseEntity<?> createProfile(@RequestBody ProfileDTO dto)
    {
        ProfileDTO save = profileServise.save(dto);
        return ResponseEntity.ok().body(save);
    }

    @GetMapping("/read")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token)
    {

        Long decode = JWTUtil.decode(token, ProfileRole.USER);
        ProfileDTO save = profileServise.getProfile(decode);
        return ResponseEntity.ok().body(save);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token,ProfileDTO dto)
    {

        Long decode = JWTUtil.decode(token, ProfileRole.USER);
        profileServise.update(decode,dto);
        return ResponseEntity.ok().build();
    }
  @DeleteMapping("/delete")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token)
    {

        Long decode = JWTUtil.decode(token, ProfileRole.USER);
        profileServise.delete(decode);
        return ResponseEntity.ok().build();
    }


}
