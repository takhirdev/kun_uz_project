package com.example.dto.article;

import com.example.dto.AttachDTO;
import com.example.dto.category.CategoryDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.dto.region.RegionDTO;
import com.example.dto.type.TypesDTO;
import com.example.entity.TypesEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;
    private String title;
    private String content;
    private String description;
    private Integer sharedCount;
    private Integer imageId;
    private AttachDTO image;
    private List<AttachDTO> imageList;
    private RegionDTO region;
    private CategoryDTO category;
    private ProfileDTO moderator;
    private ProfileDTO publisher;
    private List<TypesDTO> types;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewsCount;
    private Long likeCount;
}
