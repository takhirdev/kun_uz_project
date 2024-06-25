package com.example.dto.article;

import com.example.dto.category.CategoryDTO;
import com.example.dto.region.RegionDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticleUpdateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String content;
    @NonNull
    private Integer sharedCount;
    @NonNull
    private String imageId;
    @NonNull
    private Integer regionId;
    @NonNull
    private Integer categoryId;
    @NonNull
    private List<Integer> types;
    @NonNull
    private List<String> tagList;
}
