package com.example.dto.article;

import com.example.enums.Reaction;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class ArticleLikeDTO {
    @NonNull
    private String articleId;
    @NotBlank
    private Reaction reaction;
}
