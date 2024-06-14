package com.example.service;

import com.example.mapper.Mapper;
import com.example.dto.region.RegionCreateDTO;
import com.example.dto.region.RegionDTO;
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
        return regionRepository.findAllByLang(lang.name())
                .stream()
                .map(entity -> {
                    RegionDTO dto = new RegionDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    return dto;
                })
                .toList();
    }

    public RegionDTO update(Integer id, RegionCreateDTO dto) {
        RegionEntity entity = get(id);
            entity.setOrderNumber(dto.getOrderNumber());
            entity.setNameUz(dto.getNameUz());
            entity.setNameRu(dto.getNameRu());
            entity.setNameEn(dto.getNameEn());
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

    public RegionDTO getRegion(Integer id, Language lang) {
        RegionEntity region = get(id);
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        switch (lang) {
            case UZ -> dto.setName(region.getNameUz());
            case RU -> dto.setName(region.getNameRu());
            default -> dto.setName(region.getNameEn());
        }
        return dto;
    }
}
