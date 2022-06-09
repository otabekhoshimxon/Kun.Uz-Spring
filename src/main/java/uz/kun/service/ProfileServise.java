package uz.kun.service;
//User :Lenovo
//Date :09.06.2022
//Time :6:17
//Project Name :Kun.uzWithThymleaf

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.kun.dto.ProfileDTO;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.ProfileStatus;
import uz.kun.exception.BadRequestException;
import uz.kun.exception.ItemNotFoundException;
import uz.kun.repository.ProfileRepository;
import uz.kun.util.Convertor;
import uz.kun.util.JWTUtil;

import javax.persistence.*;
import java.util.Optional;

@Service
public class ProfileServise implements Convertor<ProfileEntity,ProfileDTO> {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO save(ProfileDTO dto) {


        if (profileRepository.findById(dto.getId()).isPresent())
        {
            throw new BadRequestException("Already registered");
        }

        ProfileEntity profile=new ProfileEntity();
        profile.setEMail(dto.getEMail());
        profile.setSurname(dto.getSurname());
        profile.setName(dto.getName());
        profileRepository.save(profile);

        ProfileDTO responseDTO=new ProfileDTO();
        responseDTO.setId(dto.getId());
        responseDTO.setRole(dto.getRole());
        responseDTO.setName(dto.getName());
        responseDTO.setSurname(dto.getSurname());
        responseDTO.setJwt(JWTUtil.encode(dto.getId(),dto.getRole()));
        return responseDTO;

    }




    @Override
    public ProfileEntity toEntity(ProfileDTO dto) {

        ProfileEntity profileEntity=new ProfileEntity();
        profileEntity.setCreated_date(dto.getCreated_date());
        profileEntity.setRole(dto.getRole());
        profileEntity.setStatus(dto.getStatus());
        profileEntity.setVisible(dto.isVisible());
        profileEntity.setId(dto.getId());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setName(dto.getName());
        profileEntity.setEMail(dto.getEMail());
        return profileEntity;
    }

    @Override
    public ProfileDTO toDTO(ProfileEntity entity) {

        ProfileDTO profileDTO=new ProfileDTO();
        profileDTO.setCreated_date(entity.getCreated_date());
        profileDTO.setRole(entity.getRole());
        profileDTO.setStatus(entity.getStatus());
        profileDTO.setVisible(entity.isVisible());
        profileDTO.setId(entity.getId());
        profileDTO.setSurname(entity.getSurname());
        profileDTO.setName(entity.getName());
        profileDTO.setEMail(entity.getEMail());
        return profileDTO;
    }

    public ProfileDTO getProfile(Long id) {
        Optional<ProfileEntity> byId = profileRepository.findById(id);

        if (!byId.isPresent())
        {
            throw new ItemNotFoundException("Profile not found");

        }
        return toDTO(byId.get());

    }

    public void update(Long id, ProfileDTO dto) {

        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (!byId.isPresent())
        {
            throw new ItemNotFoundException("Profile not found");

        }

        ProfileEntity profileEntity = byId.get();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileRepository.save(profileEntity);
    }

    public void delete(Long decode) {

        Optional<ProfileEntity> byId = profileRepository.findById(decode);
        if (!byId.isPresent())
        {
            throw new ItemNotFoundException("Profile not found");
        }
        ProfileEntity profileEntity = byId.get();
        profileEntity.setStatus(ProfileStatus.BLOCK);
        profileRepository.save(profileEntity);

    }
}
