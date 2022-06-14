package uz.kun.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import uz.kun.dto.ProfileDTO;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.ProfileStatus;
import uz.kun.exps.AlreadyExist;
import uz.kun.exps.AlreadyExistPhone;
import uz.kun.exps.BadRequestException;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.ProfileRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO profileDto) {

        Optional<ProfileEntity> entity = profileRepository.findByEmail(profileDto.getEmail());
        if (entity.isPresent()) {
            throw new AlreadyExistPhone("Already exist phone");
        }

        isValid(profileDto);

        ProfileEntity profile = new ProfileEntity();
        profile.setName(profileDto.getName());
        profile.setSurname(profileDto.getSurName());
        profile.setEmail(profileDto.getEmail());
        profile.setRole(profileDto.getRole());
        profile.setPassword(profileDto.getPassword());
        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);

        profileDto.setId(profile.getId());
        profile.setPassword(null);

        return profileDto;
    }

    public List<ProfileDTO> getList() {
        Iterable<ProfileEntity> all = profileRepository.findAllByVisible(true);
        List<ProfileDTO> dtoList = new LinkedList<>();
        all.forEach(profileEntity -> {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(profileEntity.getId());
            dto.setName(profileEntity.getName());
            dto.setSurName(profileEntity.getSurname());
            dto.setEmail(profileEntity.getEmail());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public void update(Integer pId, ProfileDTO dto) {

        Optional<ProfileEntity> profile = profileRepository.findById(pId);

        if (profile.isEmpty()) {
            throw new ItemNotFoundException("not found profile");
        }

        isValidUpdate(dto);

        ProfileEntity entity = profile.get();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurName());
        entity.setEmail(dto.getEmail());
        profileRepository.save(entity);

    }


    public void delete(Integer id) {
        Optional<ProfileEntity> profile = profileRepository.findById(id);
        if (profile.isEmpty()) {
            throw new ItemNotFoundException("not found profile");
        }
        if (!profile.get().getVisible()) {
            throw new AlreadyExist("IsVisible False edi");
        }

        profile.get().setVisible(Boolean.FALSE);
        profileRepository.save(profile.get());
    }

    private void isValidUpdate(ProfileDTO dto) {

        if (dto.getName() == null || dto.getName().length() < 3) {
            throw new BadRequestException("wrong name");
        }

        if (dto.getSurName() == null || dto.getSurName().length() < 4) {
            throw new BadRequestException("surname required.");
        }

        if (dto.getEmail() == null || dto.getEmail().length() < 3) {
            throw new BadRequestException("email required.");
        }


    }

    private void isValid(ProfileDTO dto) {

        if (dto.getName() == null || dto.getName().length() < 3) {
            throw new BadRequestException("wrong name");
        }

        if (dto.getSurName() == null || dto.getSurName().length() < 4) {
            throw new BadRequestException("surname required.");
        }

        if (dto.getEmail() == null || dto.getEmail().length() < 3) {
            throw new BadRequestException("email required.");
        }

    }

    public void saveAll(List<ProfileEntity> asList) {

        for (ProfileEntity profileEntity : asList) {

            if (profileRepository.findByEmail(profileEntity.getEmail()).isPresent())
            {

            }
            else {
                profileRepository.save(profileEntity);
            }
        }
    }

    public void save(ProfileEntity moderator) {

        if (profileRepository.findByEmail(moderator.getEmail()).isPresent())
        {

        }
        else {
            profileRepository.save(moderator);
        }
    }

    public PageImpl get(int page, int size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable= PageRequest.of(page,size,sort);

        Page<ProfileEntity> all = profileRepository.findAll(pageable);

        List<ProfileDTO> profileDTOS=new LinkedList<>();

        all.forEach(profileEntity ->
                {

                    ProfileDTO profile=new ProfileDTO();
                    profile.setEmail(profileEntity.getEmail());
                    profile.setName(profileEntity.getName());
                    profile.setSurName(profileEntity.getSurname());
                    profile.setRole(profileEntity.getRole());
                    profile.setPassword(profileEntity.getPassword());
                    profileDTOS.add(profile);

                }
                );
        int totalPages = all.getTotalPages();
        PageImpl page1=new PageImpl(profileDTOS,pageable,totalPages);
        return page1;
    }
}

