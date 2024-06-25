package com.example.dto.comment;
import com.example.enums.Reaction;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLikeDTO {
    @NonNull
    private String commentId;
    @NotBlank
    private Reaction reaction;
}
