package com.example.service;

import com.example.dto.CategoryCreateDTO;
import com.example.dto.CategoryDTO;
import com.example.dto.RegionCreateDTO;
import com.example.entity.CategoryEntity;
import com.example.enums.Language;
import com.example.exception.AppBadException;
import com.example.mapper.CategoryMapper;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryCreateDTO dto) {
        CategoryEntity saved = categoryRepository.save(toEntity(dto));
        return toDTO(saved);
    }
    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity : iterable) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<CategoryDTO> getAllByLang(Language lang) {
        List<CategoryMapper> mapperList = categoryRepository.findAllByLang(lang.name());
        List<CategoryDTO> dtoList = mapperList.stream()
                .map(entity -> {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    return dto;
                })
                .toList();
        return dtoList;
    }

    public CategoryDTO update(Integer id, CategoryCreateDTO dto) {
        CategoryEntity entity = get(id);
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
        CategoryEntity saved = categoryRepository.save(entity);
        return toDTO(saved);
    }
    public Boolean delete(Integer id) {
        CategoryEntity entity = get(id);
        categoryRepository.delete(entity);
        return true;
    }
    public CategoryDTO toDTO(CategoryEntity entity){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public CategoryEntity toEntity(CategoryCreateDTO dto){
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        return entity;
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new AppBadException("Category not found"));
    }
}
