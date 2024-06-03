package com.example.dto.article;

import com.example.dto.category.CategoryDTO;
import com.example.dto.region.RegionDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
    private Integer imageId;
    @NonNull
    private Integer sharedCount;
    @Valid
    private Integer regionID;
    @Valid
    private Integer categoryID;
}
