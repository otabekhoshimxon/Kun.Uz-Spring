package uz.kun.controller;

import org.springframework.data.domain.PageImpl;
import uz.kun.dto.TypesDTO;
import uz.kun.entity.TypesEntity;
import uz.kun.enums.Lang;
import uz.kun.enums.ProfileRole;
import uz.kun.service.TypesService;
import uz.kun.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/article_type")
@RestController
public class ArticleTypeController {
    @Autowired
    private TypesService typesService;

    // PUBLIC

    @GetMapping("/public")
    public ResponseEntity<List<TypesDTO>> getArticleList(@RequestHeader(value = "Accept-Language",defaultValue = "uz") Lang lang) {
        List<TypesDTO> list = typesService.getListByLang(lang);
        return ResponseEntity.ok().body(list);


    }/* @GetMapping("/public/{lang}")
    public ResponseEntity<List<ArticleTypeDTO>> getArticleList(@PathVariable Lang lang) {
        List<ArticleTypeDTO> list = typesService.getListByLang(lang);
        return ResponseEntity.ok().body(list);
    }*/

/*    @GetMapping("/public")
    public ResponseEntity<List<ArticleTypeDTO>> getArticleList() {
        List<ArticleTypeDTO> list = typesService.getList();
        return ResponseEntity.ok().body(list);
    }*/

    // SECURED

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TypesDTO typesDto, @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        typesService.create(typesDto);
        return ResponseEntity.ok().body("SuccessFully created");
    }
    @GetMapping("/getListForAdmin")
    public ResponseEntity<List<TypesDTO>> getlist(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<TypesDTO> list = typesService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/getListByLang")
    public ResponseEntity<List<TypesDTO>> getByLang(@RequestHeader(value = "Accept-Language", defaultValue = "uz")Lang lang) {
        List<TypesDTO> list = typesService.getListByLang(lang);
        return ResponseEntity.ok().body(list);
    }
    @PutMapping("update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody TypesEntity dto,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        typesService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        typesService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }

    @GetMapping("/getPagination")
    public ResponseEntity<PageImpl> getPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        PageImpl list = typesService.getPage(page,size);
        return ResponseEntity.ok().body(list);
    }
}
