package com.example.service;

import com.example.dto.article.ArticleTypesCreateDTO;
import com.example.dto.article.ArticleTypesDTO;
import com.example.entity.ArticleTypesEntity;
import com.example.enums.Language;
import com.example.exception.AppBadException;
import com.example.mapper.Mapper;
import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleTypesService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypesDTO create(ArticleTypesCreateDTO dto) {
        ArticleTypesEntity saved = articleTypeRepository.save(toEntity(dto));
        return toDTO(saved);
    }
    public List<ArticleTypesDTO> getAll() {
        Iterable<ArticleTypesEntity> iterable = articleTypeRepository.findAll();
        List<ArticleTypesDTO> dtoList = new LinkedList<>();
        for (ArticleTypesEntity entity : iterable) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<ArticleTypesDTO> getAllByLang(Language lang) {
        List<Mapper> mapperList = articleTypeRepository.findAllByLang(lang.name());
        List<ArticleTypesDTO> dtoList = mapperList.stream()
                .map(entity -> {
                    ArticleTypesDTO dto = new ArticleTypesDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    return dto;
                })
                .toList();
        return dtoList;
    }

    public ArticleTypesDTO update(Integer id, ArticleTypesCreateDTO dto) {
        ArticleTypesEntity entity = get(id);
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
        ArticleTypesEntity saved = articleTypeRepository.save(entity);
        return toDTO(saved);
    }
    public Boolean delete(Integer id) {
        ArticleTypesEntity entity = get(id);
        articleTypeRepository.delete(entity);
        return true;
    }
    public ArticleTypesDTO toDTO(ArticleTypesEntity entity){
        ArticleTypesDTO dto = new ArticleTypesDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public ArticleTypesEntity toEntity(ArticleTypesCreateDTO dto){
        ArticleTypesEntity entity = new ArticleTypesEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        return entity;
    }

    public ArticleTypesEntity get(Integer id) {
        return articleTypeRepository.findById(id).orElseThrow(() -> new AppBadException("type not found"));
    }
}
