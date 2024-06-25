package com.example.dto.article;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleCreateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String content;
    @NonNull
    private String imageId;
    @NonNull
    private Integer regionId;
    @NonNull
    private Integer categoryId;
    @NonNull
    private List<Integer> typeList;
    @NonNull
    private List<String> tagList;
}
