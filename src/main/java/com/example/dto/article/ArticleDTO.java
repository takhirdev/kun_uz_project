package com.example.dto.article;

import com.example.entity.TypesEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ArticleDTO {
    private String id;
    private String title;
    private String content;
    private String description;
    private Integer sharedCount;
    private Integer imageId;
    private RegionEntity region;
    private CategoryEntity category;
    private ProfileEntity moderator;
    private ProfileEntity publisher;
    private List<TypesEntity> types;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewsCount;
}
