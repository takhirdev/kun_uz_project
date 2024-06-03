package com.example.service;

import com.example.dto.type.TypesCreateDTO;
import com.example.dto.type.TypesDTO;
import com.example.entity.TypesEntity;
import com.example.enums.Language;
import com.example.exception.AppBadException;
import com.example.mapper.Mapper;
import com.example.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class TypesService {
    @Autowired
    private TypeRepository typeRepository;

    public TypesDTO create(TypesCreateDTO dto) {
        TypesEntity saved = typeRepository.save(toEntity(dto));
        return toDTO(saved);
    }
    public List<TypesDTO> getAll() {
        Iterable<TypesEntity> iterable = typeRepository.findAll();
        List<TypesDTO> dtoList = new LinkedList<>();
        for (TypesEntity entity : iterable) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<TypesDTO> getAllByLang(Language lang) {
        List<Mapper> mapperList = typeRepository.findAllByLang(lang.name());
        List<TypesDTO> dtoList = mapperList.stream()
                .map(entity -> {
                    TypesDTO dto = new TypesDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    return dto;
                })
                .toList();
        return dtoList;
    }

    public TypesDTO update(Integer id, TypesCreateDTO dto) {
        TypesEntity entity = get(id);
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
        TypesEntity saved = typeRepository.save(entity);
        return toDTO(saved);
    }
    public Boolean delete(Integer id) {
        TypesEntity entity = get(id);
        typeRepository.delete(entity);
        return true;
    }
    public TypesDTO toDTO(TypesEntity entity){
        TypesDTO dto = new TypesDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public TypesEntity toEntity(TypesCreateDTO dto){
        TypesEntity entity = new TypesEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        return entity;
    }

    public TypesEntity get(Integer id) {
        return typeRepository.findById(id).orElseThrow(() -> new AppBadException("type not found"));
    }
}
