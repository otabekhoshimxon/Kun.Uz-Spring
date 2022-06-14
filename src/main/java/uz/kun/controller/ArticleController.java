package uz.kun.controller;

import uz.kun.dto.ArticleCreateDTO;
import uz.kun.dto.ArticleDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ArticleService;
import uz.kun.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @PostMapping("/create")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleCreateDTO dto,
                                             @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
        ArticleDTO response = articleService.create(dto, profileId);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/getAllForModerator")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String jwt) {
         JwtUtil.decode(jwt, ProfileRole.MODERATOR);
        List<ArticleDTO> response = articleService.getAll();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,@RequestBody ArticleCreateDTO dto,
                                             @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
       articleService.update(id,dto, profileId);
        return ResponseEntity.ok().body("Successfully updated");
    }
      @DeleteMapping("/delete/{id}")
    public ResponseEntity<ArticleDTO> delete(@PathVariable("id") String id,
                                             @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.MODERATOR);
       articleService.delete(id);
        return ResponseEntity.ok().build();
    }






}
