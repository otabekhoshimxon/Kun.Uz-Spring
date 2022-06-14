package uz.kun.controller;
import org.springframework.data.domain.PageImpl;
import uz.kun.dto.ProfileDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ProfileService;
import uz.kun.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProfileDTO profileDto,
                                    @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN,ProfileRole.PUBLISHER);
        ProfileDTO dto = profileService.create(profileDto);
        return ResponseEntity.ok().body(dto);
    }
    @GetMapping("/getProfiles")
    public ResponseEntity<List<ProfileDTO>> getProfileList(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<ProfileDTO> list = profileService.getList();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("delete/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ProfileDTO dto, @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        profileService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        profileService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ProfileDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt);
        profileService.update(profileId, dto);
        return ResponseEntity.ok().body("Sucsessfully updated");
    }

    ///////////////////////////////////////////////////
    @GetMapping("/allProfiles")
    public ResponseEntity<PageImpl> pagination(@RequestHeader("Authorization") String jwt, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        PageImpl list = profileService.get(page, size);
        return ResponseEntity.ok().body(list);
    }

    ///////////////////////////////////////////////////
}
