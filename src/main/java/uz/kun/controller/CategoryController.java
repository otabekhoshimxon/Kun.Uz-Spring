package uz.kun.controller;

import io.swagger.annotations.Api;
import uz.kun.dto.CategoryDTO;
import uz.kun.enums.Lang;
import uz.kun.enums.ProfileRole;
import uz.kun.service.CategoryService;
import uz.kun.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/category")
@RestController
@Api(tags = "Category controller ")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // PUBLIC
    @GetMapping("/adm/getList")
    public ResponseEntity<List<CategoryDTO>> getListCategory() {
        List<CategoryDTO> list = categoryService.getList();
        return ResponseEntity.ok().body(list);
    }

    // SECURED
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        categoryService.create(categoryDto);
        return ResponseEntity.ok().body("SuccessFully created");
    }

    @GetMapping("/adm/getListForAdmin")
    public ResponseEntity<List<CategoryDTO>> getList(HttpServletRequest request) {

        HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        List<CategoryDTO> list = categoryService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/adm/getListByLang")
    public ResponseEntity<List<CategoryDTO>> getByLang(@RequestHeader(value = "Accept-Language", defaultValue = "uz")Lang lang) {
        List<CategoryDTO> list = categoryService.getListByLang(lang);
        return ResponseEntity.ok().body(list);
    }


    @PutMapping("/adm/update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody CategoryDTO dto,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        categoryService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/adm/delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        categoryService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }


}
