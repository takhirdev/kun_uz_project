package com.example.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentFilterDTO {
    private Integer id;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
    private Integer profileId;
    private String articleId;
}
