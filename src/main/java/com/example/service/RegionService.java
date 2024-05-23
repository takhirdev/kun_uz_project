package com.example.service;

import com.example.mapper.RegionMapper;
import com.example.dto.RegionCreateDTO;
import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.Language;
import com.example.exception.AppBadException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionCreateDTO dto) {
        RegionEntity saved = regionRepository.save(toEntity(dto));
        return toDTO(saved);
    }
    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : iterable) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<RegionDTO> getAllByLang(Language lang) {
        List<RegionMapper> mapperList = regionRepository.findAllByLang(lang.name());
        List<RegionDTO> dtoList = mapperList.stream()
                .map(entity -> {
                    RegionDTO dto = new RegionDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    return dto;
                })
                .toList();
        return dtoList;
    }

    public RegionDTO update(Integer id, RegionCreateDTO dto) {
        RegionEntity entity = get(id);
        if (dto.getOrderNumber()!=null){
            entity.setOrderNumber(dto.getOrderNumber());
        }
        if (dto.getNameUz()!=null){
            entity.setNameUz(dto.getNameUz());
        }
        if (dto.getNameRu()!=null){
            entity.setNameRu(dto.getNameRu());
        }
        if (dto.getNameEn()!=null){
            entity.setNameEn(dto.getNameEn());
        }
        RegionEntity saved = regionRepository.save(entity);
        return toDTO(saved);
    }
    public Boolean delete(Integer id) {
        RegionEntity entity = get(id);
        regionRepository.delete(entity);
        return true;
    }
    public RegionDTO toDTO(RegionEntity entity){
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public RegionEntity toEntity(RegionCreateDTO dto){
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        return entity;
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> new AppBadException("Region not found"));
    }
}
