package com.example.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SavedArticleDTO {
    private Integer id;
    private ArticleDTO article;
    private LocalDateTime savedDate;
}
