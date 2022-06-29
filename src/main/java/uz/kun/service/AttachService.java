package uz.kun.service;
//User :Lenovo
//Date :20.06.2022
//Time :17:06
//Project Name :Kun.uz

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.kun.dto.AttachDTO;
import uz.kun.entity.AttachEntity;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.AttachRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AttachService {


    @Autowired
    private AttachRepository repository;


    @Value("${attach.folder}")
    private String attachFolder;
    @Value("${url1}")
    private String serverUrl;

/*

   @Value("${controller.url}")
    private String path;
*/

    public AttachDTO saveToSystem(MultipartFile file) {
        try {
            String pathFolders = getYmDString();
            String uuid = UUID.randomUUID().toString();
            String extension = getExtension(file.getOriginalFilename());
            String fileName = uuid + "." + extension;
            File folder = new File(attachFolder+pathFolders);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder+pathFolders+"/"+fileName);
            Files.write(path, bytes);

            AttachEntity attachEntity = new AttachEntity(uuid, file.getOriginalFilename(), extension, file.getSize(), pathFolders);
            repository.save(attachEntity);

            String s = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
            System.out.println("Current local host : "+ s);
            AttachDTO attachDTO=new AttachDTO();
            attachDTO.setId(attachEntity.getId());
            attachDTO.setUrl(serverUrl+attachFolder+"/download/"+attachEntity.getId()+"."+attachEntity.getExtention());
            return attachDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }   public AttachEntity saveArticleImageToSystem(MultipartFile file) {
        try {
            String pathFolders = getYmDString();
            String uuid = UUID.randomUUID().toString();
            String extension = getExtension(file.getOriginalFilename());
            String fileName = uuid + "." + extension;
            File folder = new File(attachFolder+pathFolders);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder+pathFolders+"/"+fileName);
            Files.write(path, bytes);

            AttachEntity attachEntity = new AttachEntity(uuid, file.getOriginalFilename(), extension, file.getSize(), pathFolders);
            repository.save(attachEntity);


            return attachEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public byte[] loadImage(String fileName) {
        byte[] imageInByte;


     /*   fileName=getFolderPathFromUrl(fileName);
*/
        BufferedImage originalImage;
        try {
            Optional<AttachEntity> byId = repository.findById(fileName.split("\\.")[0]);
            if (byId.isEmpty())
            {
                throw new ItemNotFoundException("File Not Found");
            }
            AttachEntity attachEntity = byId.get();
            originalImage = ImageIO.read(new File("attaches/" + attachEntity.getPath()+"/"+attachEntity.getId()+"."+attachEntity.getExtention()));
        } catch (Exception e) {
            return new byte[0];
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageInByte;
    }

    public byte[] open_general(String fileName) {
        byte[] data;
        try {


            // fileName -> zari.jpg
            String path = "attaches/" + fileName;
            Path file = Paths.get( path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }



    public byte[] open_general1(String fileName) {
        Optional<AttachEntity> byId = repository.findById(fileName.split("\\.")[0]);

        if (byId.isEmpty())
        {
            throw new ItemNotFoundException("Not found");

        }

        AttachEntity attachEntity = byId.get();
        String path = "attaches/" + attachEntity.getPath()+"/"+attachEntity.getId()+"."+attachEntity.getExtention();
        Path file = Paths.get( path);
        try {
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Resource download1(String fileName) {
        try {
            String s = fileName.split("\\.")[0];
            Optional<AttachEntity> byId = repository.findById(s);
            if (byId.isEmpty()){
                throw new ItemNotFoundException("Not found");
            }

            AttachEntity attachEntity = byId.get();

            Path file = Paths.get("attaches/" + attachEntity.getPath()+"/");
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                log.info("Returned resource!");
                return resource;
            } else {
                log.error("Could not read the file!");
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }


    }
    public ResponseEntity<Resource> download(String fileName) {
        try {
            String s = fileName.split("\\.")[0];
            Optional<AttachEntity> byId = repository.findById(s);
            if (byId.isEmpty()){
                throw new ItemNotFoundException("Not found");
            }

            AttachEntity attachEntity = byId.get();


                String path = getFileFullPath(attachEntity);
                Path file = Paths.get(path);
                Resource resource = new UrlResource(file.toUri());

                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + attachEntity.getOriginName() + "\"").body(resource);
                } else {
                    throw new RuntimeException("Could not read the file!");
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }


    }

    private String getFileFullPath(AttachEntity entity) {
        return attachFolder + entity.getPath() + "/" + entity.getId() + "." + entity.getExtention();
    }
    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getFolderPathFromUrl(String url) { // 2022_6_20_f978a682-a357-4eaf-ac18-ec9482a4e58b.jpg
        String[] arr = url.split("_");
        return arr[0] + "/" + arr[1] + "/" + arr[2] + "/" + arr[3];
        // 2022/06/20/f978a682-a357-4eaf-ac18-ec9482a4e58b.jpg
    }


    public byte[] openPngOrJpg(String fileName) {

        if (fileName != null && fileName.length() > 0) {
            try {
                return loadImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    public void delete(String fileName) {

        Optional<AttachEntity> byId = repository.findById(fileName.split("\\.")[0]);
        if (byId.isEmpty())
        {
            throw new ItemNotFoundException("File not found");
        }

        AttachEntity attachEntity = byId.get();

        try {
            Files.delete(Path.of(attachFolder + attachEntity.getPath() + "/" +attachEntity.getId()+"."+ attachEntity.getExtention()));
            repository.delete(attachEntity);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }  public void delete1(String fileName) {

        Optional<AttachEntity> byId = repository.findById(fileName);
        if (byId.isEmpty())
        {
            throw new ItemNotFoundException("File not found");
        }

        AttachEntity attachEntity = byId.get();

        try {
            Files.delete(Path.of(attachFolder + attachEntity.getPath() + "/" +attachEntity.getId()+"."+ attachEntity.getExtention()));
            repository.delete(attachEntity);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public PageImpl getImage(int page, int size) {

        PageRequest of = PageRequest.of(page, size);
        Page<AttachEntity> all = repository.getAll(of);
        return new PageImpl((List) all,of,all.getTotalPages());
    }

    public AttachEntity getById(String imageId) {

      return   repository.findById(imageId).orElseThrow(()->{
           throw new ItemNotFoundException("Image not found");

        });

    }
}
