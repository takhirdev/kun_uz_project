package com.example.service;

import com.example.dto.FilterResponseDTO;
import com.example.dto.ProfileCreateDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.Role;
import com.example.enums.Status;
import com.example.exception.AppBadException;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository customRepository;

    public ProfileDTO create(ProfileCreateDTO dto) {
        ProfileEntity save = profileRepository.save(toEntity(dto));
        return toDTO(save);
    }
    
    public Page<ProfileDTO> getAll(int pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
        Page<ProfileEntity> entityPage = profileRepository.findAllByVisibleTrue(pageable);
        List<ProfileDTO> list = entityPage.getContent().stream()
                .map(this::toDTO)
                .toList();
        Long totalElements = entityPage.getTotalElements();
        return new PageImpl<ProfileDTO>(list, pageable, totalElements);
    }
    
    public ProfileDTO update(Integer id, ProfileCreateDTO dto) {
        ProfileEntity entity = get(id);

        if (dto.getName()!=null){
            entity.setName(dto.getName());
        }
        if (dto.getSurname()!=null){
            entity.setSurname(dto.getSurname());
        }

        if (dto.getEmail()!=null){
            entity.setEmail(dto.getEmail());
        }

        if (dto.getPassword()!=null){
            entity.setPassword(dto.getPassword());
        }

        if (dto.getPhone()!=null){
            entity.setPhone(dto.getPhone());
        }

        ProfileEntity saved = profileRepository.save(entity);
        return toDTO(saved);
    }
    public Boolean delete(Integer id) {
        ProfileEntity entity = get(id);
        profileRepository.delete(entity);
        return true;
    }

    public Page<ProfileDTO> filter(ProfileFilterDTO dto, Integer pageNumber, Integer pageSize) {
        FilterResponseDTO<ProfileEntity> filterResponse = customRepository.filter(dto, pageNumber, pageSize);
        List<ProfileDTO> dtoList = filterResponse.getContent().stream()
                .map(this::toDTO)
                .toList();
        Long totalCount = filterResponse.getTotalCount();
        return new PageImpl<>(dtoList,PageRequest.of(pageNumber,pageSize),totalCount);
    }

    public ProfileEntity get(Integer id){
        return profileRepository.findById(id).orElseThrow(()-> new AppBadException("profile not found"));
    }

    private ProfileEntity toEntity(ProfileCreateDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setPhone(dto.getPhone());
        entity.setRole(Role.USER);
        entity.setStatus(Status.ACTIVE);
        return entity;
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
