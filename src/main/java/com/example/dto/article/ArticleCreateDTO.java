package com.example.dto.article;

import com.example.dto.type.TypesDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
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
    @NonNull
    private Integer regionId;
    @NonNull
    private Integer categoryId;
    @NonNull
    private List<Integer> types;
}
