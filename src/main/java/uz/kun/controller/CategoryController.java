package uz.kun.controller;

import uz.kun.dto.CategoryDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.CategoryService;
import uz.kun.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // PUBLIC
    @GetMapping("/getList")
    public ResponseEntity<List<CategoryDTO>> getListCategory() {
        List<CategoryDTO> list = categoryService.getList();
        return ResponseEntity.ok().body(list);
    }

    // SECURED
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDto,
                                    @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        categoryService.create(categoryDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping("/getListForAdmin")
    public ResponseEntity<List<CategoryDTO>> getList(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<CategoryDTO> list = categoryService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }


    @PutMapping("update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody CategoryDTO dto,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        categoryService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        categoryService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }


}
