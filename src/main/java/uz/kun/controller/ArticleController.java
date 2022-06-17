package uz.kun.controller;

import uz.kun.dto.ArticleCreateDTO;
import uz.kun.dto.ArticleDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.util.HttpHeaderUtil;
import uz.kun.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @PostMapping("/mod/create")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleCreateDTO dto,
                                             HttpServletRequest request) {
        Integer id = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ArticleDTO response = articleService.create(dto, id);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/mod/getAllForModerator")
    public ResponseEntity<?> getAllForModerator(    HttpServletRequest request) {
        Integer id = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        List<ArticleDTO> response = articleService.getAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {

        List<ArticleDTO> response = articleService.getPublishedArticleList();
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/get5Last")
    public ResponseEntity<?> getLast(@RequestBody String key) {
        List<ArticleDTO> response = articleService.getLast(key);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/mod/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,@RequestBody ArticleCreateDTO dto,
                                    HttpServletRequest request) {
        Integer profileid = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
       articleService.update(id,dto, profileid);
        return ResponseEntity.ok().body("Successfully updated");
    }
    @PutMapping("/mod/publish/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, HttpServletRequest request) {
        Integer profileid = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
       articleService.publish(id, profileid);
        return ResponseEntity.ok().body("Successfully published");
    }
      @DeleteMapping("/mod/delete/{id}")
    public ResponseEntity<ArticleDTO> delete(@PathVariable("id") String id,
                                             HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
       articleService.delete(id);
        return ResponseEntity.ok().build();
    }






}
