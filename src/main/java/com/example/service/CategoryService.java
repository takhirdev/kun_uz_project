package com.example.service;

import com.example.dto.category.CategoryCreateDTO;
import com.example.dto.category.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.enums.Language;
import com.example.exception.AppBadException;
import com.example.mapper.Mapper;
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
        return categoryRepository.findAllByLang(lang.name())
                .stream()
                .map(entity -> {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    return dto;
                })
                .toList();
    }

    public CategoryDTO update(Integer id, CategoryCreateDTO dto) {
        CategoryEntity entity = get(id);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        CategoryEntity saved = categoryRepository.save(entity);
        return toDTO(saved);
    }

    public Boolean delete(Integer id) {
        CategoryEntity entity = get(id);
        categoryRepository.delete(entity);
        return true;
    }

    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryEntity toEntity(CategoryCreateDTO dto) {
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

    public CategoryDTO getCategory(Integer id, Language lang) {
        CategoryEntity category = get(id);
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        switch (lang) {
            case UZ -> dto.setName(category.getNameUz());
            case RU -> dto.setName(category.getNameRu());
            default -> dto.setName(category.getNameEn());
        }
        return dto;
    }
}
