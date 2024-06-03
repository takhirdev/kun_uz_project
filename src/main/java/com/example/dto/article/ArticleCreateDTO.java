package com.example.dto.article;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
    private Integer imageId;
    @NonNull
    private Integer regionId;
    @NonNull
    private Integer categoryId;
    @NonNull
    private Integer typeId;
}
