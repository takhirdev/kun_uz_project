package com.example.service;

import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleUpdateDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.example.entity.TypesEntity;
import com.example.enums.ArticleStatus;
import com.example.exception.AppBadException;
import com.example.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void create(ArticleCreateDTO dto) {
        ArticleEntity entity = toEntity(dto);
        articleRepository.save(entity);
    }

    public void update(String id, ArticleUpdateDTO dto) {
        ArticleEntity entity = articleRepository.findById(id).orElseThrow(() -> new AppBadException("article not found"));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setSharedCount(dto.getSharedCount());
        entity.setImageId(dto.getImageId());

        // region
        RegionEntity region = new RegionEntity();
        region.setId(dto.getRegionID());
        entity.setRegion(region);

        // category
        CategoryEntity category = new CategoryEntity();
        category.setId(dto.getCategoryID());
        entity.setCategory(category);

        articleRepository.save(entity);

    }

    public void delete(String id) {
        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new AppBadException("article not found"));
        articleRepository.delete(entity);
    }

    public void changeStatus(String id) {
        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new AppBadException("article not found"));

        ArticleStatus status = entity.getStatus();
        if (status.equals(ArticleStatus.NOT_PUBLISHED)) {
            entity.setStatus(ArticleStatus.PUBLISHED);
        } else
            entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        articleRepository.save(entity);
    }

    public List<ArticleDTO> getLast5(Integer typeID) {
        List<ArticleDTO> articleList = articleRepository.getLast5ByType(typeID)
                .stream()
                .map(this::toDTO)
                .toList();
        return articleList;
    }

    public List<ArticleDTO> getLast3(Integer typeID) {
        List<ArticleDTO> articleList = articleRepository.getLast3ByType(typeID)
                .stream()
                .map(this::toDTO)
                .toList();
        return articleList;
    }

    public ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setSharedCount(entity.getSharedCount());
        dto.setImageId(entity.getImageId());
        dto.setCategory(entity.getCategory());
        dto.setContent(entity.getContent());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setRegion(entity.getRegion());
        dto.setStatus(entity.getStatus());
        dto.setTypes(entity.getTypes());
        dto.setViewsCount(entity.getViewsCount());
        dto.setModerator(entity.getModerator());
        return dto;
    }

    public ArticleEntity toEntity(ArticleCreateDTO dto) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(dto.getTitle());
        articleEntity.setDescription(dto.getDescription());
        articleEntity.setContent(dto.getContent());

        // region
        RegionEntity region = new RegionEntity();
        region.setId(dto.getRegionId());

        // category
        CategoryEntity category = new CategoryEntity();
        category.setId(dto.getCategoryId());

        // article types
        TypesEntity types = new TypesEntity();
        types.setId(dto.getTypeId());
        List<TypesEntity> list = new ArrayList<>();
        list.add(types);
        articleEntity.setTypes(list);

        articleEntity.setRegion(region);
        articleEntity.setCategory(category);
        articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);

        return articleEntity;
    }
}
