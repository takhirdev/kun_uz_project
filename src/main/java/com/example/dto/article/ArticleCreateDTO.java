package com.example.article;

import com.example.dto.category.CategoryDTO;
import com.example.dto.region.RegionDTO;
import jakarta.validation.Valid;
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
    private Integer imageId;
    @Valid
    private RegionDTO region;
    @Valid
    private CategoryDTO category;
    @Valid
    private List <@NotBlank String> articleTypes;
}
