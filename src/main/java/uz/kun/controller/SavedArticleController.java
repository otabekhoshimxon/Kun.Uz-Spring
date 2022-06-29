package uz.kun.controller;
//User :Lenovo
//Date :17.06.2022
//Time :14:04
//Project Name :Kun.uz

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.SavedArticleDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.SavedArticleService;
import uz.kun.util.HttpHeaderUtil;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/saved_article")
@Api(tags = "Saved controller ")
public class SavedArticleController {


    @Autowired
    private SavedArticleService service;


    @PostMapping("/create")
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody String articleId)
    {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.USER);
        service.create(profileId,articleId);
        return ResponseEntity.ok("Successfully saved");
    }
  @DeleteMapping("/delete")
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody String articleId)
    {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.USER);
        service.delete(profileId,articleId);
        return ResponseEntity.ok("Successfully deleted");
    }
 @GetMapping("/getAll")
    public ResponseEntity<?> get(HttpServletRequest request)
    {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.USER);
        List<SavedArticleDTO> all = service.getAll(profileId);
        return ResponseEntity.ok(all);
    }



}
