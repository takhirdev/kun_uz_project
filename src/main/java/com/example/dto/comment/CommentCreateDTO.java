package com.example.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {
    @NotBlank
    private String content;
    @NotBlank
    private String articleId;
    private String replId;
}
