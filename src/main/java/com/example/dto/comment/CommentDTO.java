package com.example.dto.comment;

import com.example.dto.article.ArticleDTO;
import com.example.dto.profile.ProfileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private String id;
    private String content;
    private Integer profileId;
    private ProfileDTO profile;
    private String articleId;
    private ArticleDTO article;
    private String replyId;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private Boolean visible;
}
