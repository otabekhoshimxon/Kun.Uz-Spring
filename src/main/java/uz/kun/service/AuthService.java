package uz.kun.service;

import uz.kun.dto.AuthDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.RegistrationDTO;
import uz.kun.dto.VerificationDTO;
import uz.kun.dto.integration.ResponseInfoDTO;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;
import uz.kun.exps.BadRequestException;
import uz.kun.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.util.JWTUtil;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

  @Autowired
    private MailServise mailServise;
 @Autowired
    private ProfileService profileService;


    @Autowired
    private SmsService smsService;
    public ProfileDTO login(AuthDTO authDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(authDTO.getEmail());
        if (optional.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        ProfileEntity profile = optional.get();
        System.err.println(profile.getId());
        if (!profile.getPassword().equals(authDTO.getPassword())) {
            throw new BadRequestException("User not found");
        }

        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("No ruxsat");
        }

        ProfileDTO dto = new ProfileDTO();
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setAuthorization(JWTUtil.encode(profile.getId(), profile.getRole()));

        return dto;
    }
    public String registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        entity.setStatus(ProfileStatus.BLOCK);
        entity.setRole(ProfileRole.USER);
        profileRepository.save(entity);
       // mailServise.sendRegistrationEmail(dto.getEmail(), entity.getId());

        smsService.sendRegistrationSms(dto.getPhone());

        return "Sms sended";
    }


    public ResponseInfoDTO resendSms(String phone) {
        Long count = smsService.getSmsCount(phone);
        if (count >= 4) {
            return new ResponseInfoDTO(-1, "Limit dan o'tib getgan");
        }

        smsService.sendRegistrationSms(phone);
        return new ResponseInfoDTO(1);
    }

    public String emailVerification(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            return "<h1>User Not Found</h1>";
        }


        ProfileEntity profile = optional.get();
        if (profile.getStatus().equals(ProfileStatus.ACTIVE))
        {
            return    "<h1>Siz oldin ro'yxatdan o'tgansiz </h1>";

        }
        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);
        return "<h1 style='align-text:center'> Tabriklaymiz.</h1>";
    }


    public ResponseInfoDTO resendEmail(String email) {

        Long count = mailServise.getEmailCount(email);
        if (count >= 4) {
            return new ResponseInfoDTO(-1, "Limit dan o'tib getgan");
        }


        ProfileEntity byEmail = profileService.findByEmail(email);
        mailServise.sendRegistrationEmail(email, byEmail.getId());
        return new ResponseInfoDTO(1);
    }
}
