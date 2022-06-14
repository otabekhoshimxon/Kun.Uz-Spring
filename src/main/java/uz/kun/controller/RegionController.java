package uz.kun.controller;

import uz.kun.dto.RegionDto;
import uz.kun.enums.ProfileRole;
import uz.kun.service.RegionService;
import uz.kun.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> create(@RequestBody RegionDto regionDto, @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        regionService.create(regionDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping("getList/admin")
    public ResponseEntity<List<RegionDto>> getlist(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<RegionDto> list = regionService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody RegionDto dto,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        regionService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        regionService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }


}
