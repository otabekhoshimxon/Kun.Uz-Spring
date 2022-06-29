package uz.kun.controller;
//User :Lenovo
//Date :20.06.2022
//Time :17:06
//Project Name :Kun.uz

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.kun.dto.AttachDTO;
import uz.kun.entity.AttachEntity;
import uz.kun.enums.ProfileRole;
import uz.kun.service.AttachService;
import uz.kun.util.HttpHeaderUtil;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/attach")
@Api(tags = "Attach controller ")
public class AttachController {

    @Autowired
    private AttachService attachService;


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        log.info("File uploaded {}",file);
        AttachDTO attachDTO = attachService.saveToSystem(file);
        return ResponseEntity.ok().body(attachDTO);
    }
    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
       return attachService.openPngOrJpg(fileName);
    }


    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return attachService.open_general1(fileName);
    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
       return attachService.download(fileName);

    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<?>delete(@PathVariable("filename") String fileName, HttpServletRequest servletRequest)
    {
        HttpHeaderUtil.getId(servletRequest, ProfileRole.ADMIN);
        attachService.delete(fileName);
        return ResponseEntity.ok().build();
    }
  @GetMapping("/getImages")
    public ResponseEntity<PageImpl>fi(@RequestBody int page, @RequestBody int size, HttpServletRequest servletRequest)
    {
        HttpHeaderUtil.getId(servletRequest, ProfileRole.ADMIN);
        PageImpl image = attachService.getImage(page, size);

        return ResponseEntity.ok().body(image);
    }




}
