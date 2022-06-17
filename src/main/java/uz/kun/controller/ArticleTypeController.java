package uz.kun.controller;

import org.springframework.data.domain.PageImpl;
import uz.kun.dto.TypesDTO;
import uz.kun.entity.TypesEntity;
import uz.kun.enums.Lang;
import uz.kun.enums.ProfileRole;
import uz.kun.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.util.HttpHeaderUtil;
import uz.kun.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody TypesDTO typesDto,  HttpServletRequest request) {
      HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        typesService.create(typesDto);
        return ResponseEntity.ok().body("SuccessFully created");
    }
    @GetMapping("/adm/getListForAdmin")
    public ResponseEntity<List<TypesDTO>> getlist( HttpServletRequest request) {
         HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        List<TypesDTO> list = typesService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/getListByLang")
    public ResponseEntity<List<TypesDTO>> getByLang(@RequestHeader(value = "Accept-Language", defaultValue = "uz")Lang lang) {
        List<TypesDTO> list = typesService.getListByLang(lang);
        return ResponseEntity.ok().body(list);
    }
    @PutMapping("/adm/update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody TypesEntity dto,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        typesService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/adm/delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        typesService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }

    @GetMapping("/getPagination")
    public ResponseEntity<PageImpl> getPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        PageImpl list = typesService.getPage(page,size);
        return ResponseEntity.ok().body(list);
    }
}
