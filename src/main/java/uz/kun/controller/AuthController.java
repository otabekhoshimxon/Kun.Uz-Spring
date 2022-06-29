package uz.kun.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.AuthDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.RegistrationDTO;
import uz.kun.dto.VerificationDTO;
import uz.kun.dto.integration.ResponseInfoDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import uz.kun.service.MailServise;
import uz.kun.service.SmsService;
import uz.kun.util.HttpHeaderUtil;
import uz.kun.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Slf4j
@Api(tags = "Authorization and Registration")
public class AuthController {


    @Autowired
    private AuthService authService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private MailServise mailServise;


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        log.info("Request for registration {}", dto);
        String registration = authService.registration(dto);
        return ResponseEntity.ok().body(registration);
    }

    @ApiOperation(value = "Method for login")
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        log.info("Request for login {}", dto);
        ProfileDTO profileDto = authService.login(dto);
        return ResponseEntity.ok(profileDto);
    }

    @ApiOperation(value = "Method for verification")
    @PostMapping("/verification")
    public ResponseEntity<?> login(@RequestBody VerificationDTO dto) {
        log.info("Request for verification {}", dto);
        ProfileDTO verification = smsService.verification(dto);
        return ResponseEntity.ok(verification);
    }

    @ApiOperation(value = "Resend Phone", notes = "Method for resend with phone")
    @GetMapping("/sms/resend/{phone}")
    public ResponseEntity<ResponseInfoDTO> resendSms(@ApiParam(value = "phone", readOnly = true, example = "+998901234567") @PathVariable("phone") String phone) {
        ResponseInfoDTO response = authService.resendSms(phone);
        return ResponseEntity.ok(response);

    }

    @ApiOperation(value = "Resend Email", notes = "Method for resend with email")

    @GetMapping("/email/resend/{email}")
    public ResponseEntity<ResponseInfoDTO> resendEmail(@ApiParam(value = "email", example = "otabek@mail.ru") @PathVariable("email") String phone) {
        ResponseInfoDTO response = authService.resendEmail(phone);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/log")
    public ResponseEntity<ProfileDTO> log(Model model) {
        AuthDTO authDTO = new AuthDTO();
        model.addAttribute("auth", authDTO);
        ProfileDTO profileDto = authService.login(authDTO);
        return ResponseEntity.ok(profileDto);
    }

    @GetMapping("/email/verification/{token}")
    public ResponseEntity<String> login(@PathVariable("token") String token) {
        Integer id = JWTUtil.decode(token);
        String response = authService.emailVerification(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/email/pagination")
    public ResponseEntity<?> getPagination(@RequestBody int page, @RequestBody int size, HttpServletRequest httpServletRequest) {


        HttpHeaderUtil.getId(httpServletRequest, ProfileRole.ADMIN);
        PageImpl page1 = mailServise.getPage(page, size);
        return ResponseEntity.ok(page1);
    }


}
