package uz.kun.service;
//User :Lenovo
//Date :22.06.2022
//Time :17:45
//Project Name :Kun.uz

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.kun.dto.ProfileDTO;
import uz.kun.dto.SmsDTO;
import uz.kun.dto.VerificationDTO;
import uz.kun.dto.integration.SmsRequestDTO;
import uz.kun.dto.integration.SmsResponseDTO;
import uz.kun.entity.ProfileEntity;
import uz.kun.entity.SmsEntity;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;
import uz.kun.exps.BadRequestException;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.ProfileRepository;
import uz.kun.repository.SmsRepository;
import uz.kun.util.Convertor;
import uz.kun.util.JWTUtil;
import uz.kun.util.RandomUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SmsService {

@Autowired
private SmsRepository repository;
@Autowired
private ProfileRepository profileRepository;
@Autowired
private Convertor convertor;
@Autowired
private RestTemplate restTemplate;


private String smsUrl="https://api.smsfly.uz/";

private String key="4b77045c-3a12-11eb-9bb4-6799ca27647f";
    public void sendRegistrationSms(String phone) {
        String code = RandomUtil.getRandomSmsCode();
        String message = "Hurmatli  mijoz sizga havfsizlik uchun\n himoya kodi: [ " + code+" ] ";

        SmsResponseDTO responseDTO = send(phone, message);

        SmsEntity entity = new SmsEntity();
        entity.setPhone(phone);
        entity.setCode(code);
        entity.setStatus(responseDTO.getSuccess());

        repository.save(entity);
        send(phone,message);
    }

    private SmsResponseDTO send(String phone, String message) {
        SmsRequestDTO requestDTO = new SmsRequestDTO();
        requestDTO.setKey(key);
        requestDTO.setPhone(phone);
        requestDTO.setMessage(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SmsRequestDTO> entity = new HttpEntity<SmsRequestDTO>(requestDTO, headers);


        SmsResponseDTO response = restTemplate.postForObject(smsUrl, entity, SmsResponseDTO.class);
        return response;
    }



    public Long getSmsCount(String phone)
    {
       return repository.getSmsCount(phone);
    }


    public ProfileDTO verification(VerificationDTO dto) {
        Optional<SmsEntity> optional = repository.findTopByPhoneOrderByCreatedDateDesc(dto.getPhone());
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Phone Not Found");
        }

        SmsEntity sms = optional.get();
        LocalDateTime validDate = sms.getCreatedDate().plusMinutes(1);

        if (!sms.getCode().equals(dto.getCode())) {
            throw new BadRequestException("Code Invalid");
        }
        if (validDate.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Time is out");
        }

        profileRepository.updateStatusByPhone(dto.getPhone(), ProfileStatus.ACTIVE);

        Optional<ProfileEntity> byPhone = profileRepository.findByPhone(dto.getPhone());

        ProfileDTO dto1 = Convertor.entityToDTO(byPhone.get());
        dto1.setAuthorization(JWTUtil.encode(dto1.getId(), ProfileRole.USER));
        return dto1;
    }

    public PageImpl getSms(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<SmsEntity> smsEntity = repository.findAllByStatusTrue(pageable);
        List<SmsDTO> sms=new ArrayList<>();
        smsEntity.forEach(smsEntity1 -> {
           sms.add(convertor.entityToDTO(smsEntity1));
        });
        PageImpl page1=new PageImpl<>(sms,pageable,smsEntity.getTotalPages());
        return page1;
    }
}
