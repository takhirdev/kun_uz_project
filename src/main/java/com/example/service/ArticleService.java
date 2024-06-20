package com.example.service;

import com.example.dto.FilterResponseDTO;
import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleFilterDTO;
import com.example.dto.article.ArticleUpdateDTO;
import com.example.entity.*;
import com.example.enums.ArticleStatus;
import com.example.enums.Language;
import com.example.exception.AppBadException;
import com.example.mapper.ArticleShortInfoMapper;
import com.example.repository.ArticleCustomRepository;
import com.example.repository.ArticleRepository;
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
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final ArticleCustomRepository articleCustomRepository;
    private final AttachService attachService;

    public ArticleService(ArticleRepository articleRepository,
                          ArticleTypesService articleTypesService, RegionService regionService, CategoryService categoryService, ArticleCustomRepository articleCustomRepository, AttachService attachService) {
        this.articleRepository = articleRepository;
        this.articleTypesService = articleTypesService;
        this.regionService = regionService;
        this.categoryService = categoryService;
        this.articleCustomRepository = articleCustomRepository;
        this.attachService = attachService;
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

        if (entity.getStatus().equals(ArticleStatus.NOT_PUBLISHED)) {
            entity.setStatus(ArticleStatus.PUBLISHED);
        } else
            entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        articleRepository.save(entity);
    }

    public List<ArticleDTO> getLast5ByTypeId(Integer typeID) {
        return iterateStream(articleRepository.getLastNArticleByType(typeID, 5));
    }

    public List<ArticleDTO> getLast3ByTypeId(Integer typeID) {
        return iterateStream(articleRepository.getLastNArticleByType(typeID, 3));
    }

    public List<ArticleDTO> last8Article(List<String> idList) {
        return iterateStream(articleRepository.getLast8(idList));
    }

    public List<ArticleDTO> last4ExcludingId(Integer typeId, Integer articleId) {
        return iterateStream(articleRepository.getLast4ArticlesByTypeExcludingId(typeId, articleId));
    }

    public List<ArticleDTO> mostRead4Article() {
        return iterateStream(articleRepository.getMostRead4Article());
    }

    public List<ArticleDTO> last5ByTypeIdAndRegionId(Integer typeId, Integer regionId) {
        return iterateStream(articleRepository.get5ArticleByTypeIdAndRegionId(typeId, regionId));
    }

    public List<ArticleDTO> paginationByRegion(Integer regionId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return iterateStream( articleRepository.paginationByRegion(regionId,pageable));
    }

    public List<ArticleDTO> last5ByCategoryId(Integer categoryId) {
       return iterateStream(articleRepository.get5ArticleByCategoryId(categoryId));
    }

    public List<ArticleDTO> paginationByCategory(Integer categoryId,Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return iterateStream(articleRepository.paginationByCategory(categoryId,pageable));
    }

    public void increaseViewCount(String articleId) {   //   viewcount/{articleId}
        articleRepository.increaseViewCount(articleId);
    }

    public void increaseShareCount(String articleId) {   //   viewcount/{articleId}
        articleRepository.increaseShareCount(articleId);
    }

    public Page<ArticleDTO> filter(ArticleFilterDTO dto, Integer pageNumber, Integer pageSize) {
        FilterResponseDTO<ArticleShortInfoMapper> filterResponse = articleCustomRepository.filter(dto, pageNumber, pageSize);

        List<ArticleDTO> list = iterateStream(filterResponse.getContent());

        Long totalCount = filterResponse.getTotalCount();

        return new PageImpl<>(list, PageRequest.of(pageNumber,pageSize), totalCount);
    }

    public ArticleDTO getById(String articleId, Language language) {
        ArticleEntity entity = getById(articleId);
        if (!entity.getStatus().equals(ArticleStatus.PUBLISHED)) {
            throw new AppBadException("Article not found");
        }
        return toDTOFullInfo(entity,language);
    }

    public ArticleEntity getById(String articleId) {
        return articleRepository.findByIdAndVisibleTrue(articleId)
                .orElseThrow(() -> new AppBadException("article not found"));
    }

    private List<ArticleDTO> iterateStream(List<ArticleShortInfoMapper> articles) {
        return articles.stream()
                .map(this::toDTOShortInfo)
                .toList();
    }

    public ArticleDTO toDTOShortInfo(ArticleShortInfoMapper mapper) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setPublishedDate(mapper.getPublishedDate());
        dto.setImage(attachService.getDTOWithURL(mapper.getImageId()));
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

    public ArticleDTO toDTOFullInfo(ArticleEntity entity,Language language) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setRegion(regionService.getRegion(entity.getRegionId(), language));
        dto.setCategory(categoryService.getCategory(entity.getCategoryId(), language));
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setViewsCount(entity.getViewsCount());
//        dto.setLikeCount(articleLikeRepository.getArticleLikeCount(id)); // TODO
//         tagList(name)
        return dto;
    }
}
