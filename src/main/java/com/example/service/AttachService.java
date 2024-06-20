package com.example.service;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.exception.AppBadException;
import com.example.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class AttachService {
    private final AttachRepository attachRepository;

    @Value("${server.url}")
    private String serverUrl;

    @Value("${upload.path}")
    private String uploadPath;

    public AttachService(AttachRepository attachRepository) {
        this.attachRepository = attachRepository;
    }


    public AttachDTO upload(MultipartFile file) {
        String key = UUID.randomUUID().toString();                     // safsfsf-sfwf-11sdd-wef1
        String extension = getExtension(file.getOriginalFilename());   // png/mp3/doc
        String pathFolder = getYMDString();   // 2024/6/11
        try {
            File folder = new File(uploadPath + "/" + pathFolder, key + "." + extension); //"C:/Users/takhi/OneDrive/Desktop/uploadFolder"/2024/6/20
            if (!folder.exists()) {
                folder.mkdirs();
            }

            file.transferTo(folder);

            AttachEntity entity = new AttachEntity();
            entity.setId(key + "." + extension);
            entity.setOriginalName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setSize(file.getSize());
            entity.setPath(pathFolder);
            attachRepository.save(entity);
            return toDTO(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] open_general(String attachId) {
        byte[] data;
        try {
            AttachEntity entity = get(attachId);
            String path = entity.getPath() + "/" + attachId; // 2024/06/11/asd-ewaz-1qws-ascd.png
            Path file = Paths.get(uploadPath + "/" + path);   // C:/Users/takhi/OneDrive/Desktop/uploadFolder/2024/06/11/asd-fdsa-sdf-sdc.png
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public Resource download(String attachId) {
        try {
            AttachEntity entity = get(attachId);
            String path = entity.getPath() + "/" + attachId;
            Path file = Paths.get(uploadPath + "/" + path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void delete(String attachId) {
        AttachEntity entity = get(attachId);
        File file = new File(uploadPath + "/" + entity.getPath() + "/" + attachId);
        if (!file.delete()) {
            throw new AppBadException("file not deleted");
        } else {
            attachRepository.deleteById(attachId);
        }
    }

    public AttachDTO getDTOWithURL(String attachId) {
        AttachDTO dto = new AttachDTO();
        dto.setId(attachId);
        dto.setUrl(serverUrl + uploadPath +  attachId); /////
        return dto;
    }

    private String getYMDString() {
        LocalDate date = LocalDate.now();
        return date.getYear() + "/" + date.getMonthValue() + "/" + date.getDayOfMonth();
    }

    private String getExtension(String filename) {
        int lastIndex = filename.lastIndexOf('.');
        return filename.substring(lastIndex + 1);
    }

    public AttachEntity get(String attachId) {
        return attachRepository.findById(attachId)
                .orElseThrow(() -> new AppBadException("file not found"));
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setCreatedData(entity.getCreatedData());
        dto.setExtension(entity.getExtension());
        dto.setSize(entity.getSize());
        dto.setOriginalName(entity.getOriginalName());
        dto.setPath(entity.getPath());
        dto.setUrl(serverUrl + "/" + entity.getId());
        return dto;
    }
}
