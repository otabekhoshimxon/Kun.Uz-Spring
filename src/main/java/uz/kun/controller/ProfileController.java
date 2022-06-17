package uz.kun.controller;
import org.springframework.data.domain.PageImpl;
import uz.kun.dto.ProfileDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.util.HttpHeaderUtil;
import uz.kun.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProfileDTO profileDto
            , HttpServletRequest request) {
        Integer profileid = HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        ProfileDTO dto = profileService.create(profileDto);
        return ResponseEntity.ok().body(dto);
    }




    @GetMapping("/getProfiles")
    public ResponseEntity<List<ProfileDTO>> getProfileList(HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<ProfileDTO> list = profileService.getList();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("delete/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ProfileDTO dto, HttpServletRequest request) {
         HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        profileService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
         HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        profileService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ProfileDTO dto, HttpServletRequest request) {
        Integer profileid = HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        profileService.update(profileid, dto);
        return ResponseEntity.ok().body("Sucsessfully updated");
    }

    ///////////////////////////////////////////////////
    @GetMapping("/allProfiles")
    public ResponseEntity<PageImpl> pagination( @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size, HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        PageImpl list = profileService.get(page, size);
        return ResponseEntity.ok().body(list);
    }

    ///////////////////////////////////////////////////
}
