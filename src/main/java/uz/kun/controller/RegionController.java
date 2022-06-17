package uz.kun.controller;

import uz.kun.dto.RegionCreateDto;
import uz.kun.dto.RegionDto;
import uz.kun.enums.Lang;
import uz.kun.enums.ProfileRole;
import uz.kun.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.util.HttpHeaderUtil;
import uz.kun.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/region")
@RestController
public class RegionController {
    @Autowired
    private RegionService regionService;

    // PUBLIC
    @GetMapping("/getlist")
    public ResponseEntity<List<RegionDto>> getListRegion() {
        List<RegionDto> list = regionService.getList();
        return ResponseEntity.ok().body(list);
    }

    // SECURE
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RegionCreateDto regionDto, HttpServletRequest request) {
        HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        regionService.create(regionDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping("getList/admin")
    public ResponseEntity<List<RegionDto>> getlist(   HttpServletRequest request) {
        HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        List<RegionDto> list = regionService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("getListByLang")
    public ResponseEntity<List<RegionDto>> getByLang(@RequestHeader(value = "Accept-Language", defaultValue = "uz")Lang lang) {
        List<RegionDto> list = regionService.getListByLang(lang);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody RegionDto dto,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        regionService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        regionService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }


}
