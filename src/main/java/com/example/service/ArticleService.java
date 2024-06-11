package com.example.service;

import com.example.dto.FilterResponseDTO;
import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleFilterDTO;
import com.example.dto.article.ArticleUpdateDTO;
import com.example.entity.*;
import com.example.enums.ArticleStatus;
import com.example.exception.AppBadException;
import com.example.repository.ArticleCustomRepository;
import com.example.repository.ArticleRepository;
import com.example.repository.ArticleTypesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTypesService articleTypesService;
    private final ArticleTypesRepository articleTypesRepository;
    private ArticleCustomRepository articleCustomRepository;

    public ArticleService(ArticleRepository articleRepository,
                          ArticleTypesService articleTypesService,
                          ArticleTypesRepository articleTypesRepository,
                          ArticleCustomRepository articleCustomRepository) {
        this.articleRepository = articleRepository;
        this.articleTypesService = articleTypesService;
        this.articleTypesRepository = articleTypesRepository;
        this.articleCustomRepository = articleCustomRepository;
    }

    public void create(ArticleCreateDTO dto) {
        ArticleEntity entity = toEntity(dto);
        articleRepository.save(entity);
        articleTypesService.create(entity.getId(), dto.getTypes());
    }


    public void update(String articleId, ArticleUpdateDTO dto) {
        ArticleEntity entity = getById(articleId);

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());

        articleRepository.save(entity);
        articleTypesService.merge(articleId, dto.getTypes());

    }

    public void delete(String articleId) {
        ArticleEntity entity = getById(articleId);
        articleRepository.delete(entity);
    }

    public void changeStatus(String articleId) {
        ArticleEntity entity = getById(articleId);

        ArticleStatus status = entity.getStatus();

        if (status.equals(ArticleStatus.NOT_PUBLISHED)) {
            entity.setStatus(ArticleStatus.PUBLISHED);
        } else
            entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        articleRepository.save(entity);
    }

    public List<ArticleDTO> getLast5ByTypeId(Integer typeID) {
        Pageable limit = PageRequest.of(0, 5);
        return articleTypesRepository.getLastNArticleByTypeId(typeID, limit)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ArticleDTO> getLast3ByTypeId(Integer typeID) {
        Pageable limit = PageRequest.of(0, 3);
        return articleTypesRepository.getLastNArticleByTypeId(typeID, limit)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ArticleDTO getByArticleId(String articleId) {
        return toDTO(getById(articleId));
    }

    public List<ArticleDTO> getByIdNotInGivenList(List<Integer> typeList) {
        return articleRepository.getLast8ArticleByIdNotInList(typeList)
                .stream()
                .map(this::toShortInfoDTO)
                .toList();
    }

    public List<ArticleDTO> getByTypeIdExceptId(Integer typeId, Integer articleId) {
        Pageable limit = PageRequest.of(0, 4);
        return articleTypesRepository.getLastNArticleByTypeIdExceptArticleId(typeId, articleId, limit)
                .stream()
                .map(this::toShortInfoDTO)
                .toList();
    }

    public ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(dto.getContent());
        dto.setDescription(entity.getDescription());
        dto.setSharedCount(entity.getSharedCount());
        dto.setImageId(entity.getImageId());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setRegion(entity.getRegion());
        dto.setCategory(entity.getCategory());
        dto.setViewsCount(entity.getViewsCount());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setModerator(entity.getModerator());
        dto.setPublisher(entity.getPublisher());
        return dto;
    }

    public ArticleDTO toShortInfoDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImageId(entity.getImageId());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }

    public ArticleEntity toEntity(ArticleCreateDTO dto) {

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(dto.getTitle());
        articleEntity.setDescription(dto.getDescription());
        articleEntity.setContent(dto.getContent());
        articleEntity.setImageId(dto.getImageId());
        articleEntity.setRegionId(dto.getRegionId());
        articleEntity.setCategoryId(dto.getCategoryId());
        articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);

        return articleEntity;
    }

    public ArticleEntity getById(String articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new AppBadException("article not found"));
    }

    public Page<ArticleDTO> filter(ArticleFilterDTO dto, Integer pageNumber, Integer pageSize) {
        FilterResponseDTO<ArticleEntity> filterResponse = articleCustomRepository.filter(dto, pageNumber, pageSize);

        List<ArticleDTO> list = filterResponse.getContent()
                .stream()
                .map(this::toShortInfoDTO)
                .toList();

        Long totalCount = filterResponse.getTotalCount();

        return new PageImpl<>(list, PageRequest.of(pageNumber,pageSize), totalCount);
    }
}
