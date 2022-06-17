package uz.kun.service;

import uz.kun.dto.AuthDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.RegistrationDTO;
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
    public ProfileDTO login(AuthDTO authDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(authDTO.getEmail());
        if (optional.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        ProfileEntity profile = optional.get();
        if (!profile.getPassword().equals(authDTO.getPassword())) {
            throw new BadRequestException("User not found");
        }

        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("No ruxsat");
        }

        ProfileDTO dto = new ProfileDTO();
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setJwt(JWTUtil.encode(profile.getId(), profile.getRole()));

        return dto;
    }
    public ProfileDTO registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(ProfileRole.USER);
        profileRepository.save(entity);

        ProfileDTO responseDTO = new ProfileDTO();
        responseDTO.setName(dto.getName());
        responseDTO.setSurname(dto.getSurname());
        responseDTO.setEmail(dto.getEmail());
        responseDTO.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));
        return responseDTO;
    }
}
