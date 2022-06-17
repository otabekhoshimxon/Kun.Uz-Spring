package uz.kun.controller;

import org.springframework.ui.Model;
import uz.kun.dto.AuthDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.RegistrationDTO;
import uz.kun.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        ProfileDTO response = authService.registration(dto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        ProfileDTO profileDto = authService.login(dto);
        return ResponseEntity.ok(profileDto);
    }

    @PostMapping("/log")
    public ResponseEntity<ProfileDTO> log(Model model) {
        AuthDTO authDTO=new AuthDTO();
        model.addAttribute("auth",authDTO);
        ProfileDTO profileDto = authService.login(authDTO);
        return ResponseEntity.ok(profileDto);
    }


}
