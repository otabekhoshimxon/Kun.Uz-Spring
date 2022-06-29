package uz.kun.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.ProfileFilterDTO;
import uz.kun.entity.AttachEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.ProfileStatus;
import uz.kun.exps.AlreadyExist;
import uz.kun.exps.AlreadyExistPhone;
import uz.kun.exps.BadRequestException;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.CustomProfileRepository;
import uz.kun.repository.ProfileRepository;
import uz.kun.util.Convertor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

  @Autowired
    private CustomProfileRepository customProfileRepository;

  @Autowired
    private Convertor convertor;


    @Autowired
    private AttachService attachService;
    public ProfileDTO create(ProfileDTO profileDto) {

        Optional<ProfileEntity> entity = profileRepository.findByEmail(profileDto.getEmail());
        if (entity.isPresent()) {
            throw new AlreadyExistPhone("Already exist phone");
        }

        isValid(profileDto);

        ProfileEntity profile = new ProfileEntity();
        profile.setName(profileDto.getName());
        profile.setSurname(profileDto.getSurname());
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
            dto.setSurname(profileEntity.getSurname());
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
        entity.setSurname(dto.getSurname());
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

        if (dto.getSurname() == null || dto.getSurname().length() < 4) {
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

        if (dto.getSurname() == null || dto.getSurname().length() < 4) {
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
                    profile.setSurname(profileEntity.getSurname());
                    profile.setRole(profileEntity.getRole());
                    profile.setPassword(profileEntity.getPassword());
                    profileDTOS.add(profile);

                }
                );
        int totalPages = all.getTotalPages();
        PageImpl page1=new PageImpl(profileDTOS,pageable,totalPages);
        return page1;
    }


    public void updateImage(Integer id, String imageId) {


        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (byId.isEmpty())
        {
         throw new BadRequestException("User not found");

        }

        ProfileEntity entity = byId.get();
        System.err.println(entity);

        AttachEntity image = attachService.getById(imageId);
        if (image==null){
            throw new ItemNotFoundException("Image not found");
        }
        String old = image.getId();

        if (entity.getImage()==null && imageId!=null){

            entity.setImage(new AttachEntity(imageId));

        } else if (entity.getImage()!=null && imageId==null) {

            entity.setImage(null);
            profileRepository.save(entity);
            attachService.delete1(old);

        } else if (entity.getImage()!=null && imageId!=null && !entity.getImage().getId().equals(imageId)) {

            entity.setImage(new AttachEntity(imageId));
            attachService.delete1(entity.getImage().getId());
        }

        entity.setImage(new AttachEntity(imageId));
        profileRepository.save(entity);
        System.err.println(entity);









    }

    public ProfileEntity findByEmail(String email) {
        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(email);
        if (byEmail.isEmpty())
        {
            throw new ItemNotFoundException("Email not found");
        }
        return byEmail.get();
    }


    public List<ProfileDTO> filter(ProfileFilterDTO filterDTO){
        List<ProfileEntity> filter = customProfileRepository.filter(filterDTO);
        List<ProfileDTO> profileDTOS=new ArrayList<>();

        for (ProfileEntity profileEntity : filter) {
            ProfileDTO dto = convertor.entityToDTO(profileEntity);
            profileDTOS.add(dto);
        }
        return profileDTOS;
    }
}

