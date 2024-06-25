package com.example.controller;

import com.example.dto.comment.CommentCreateDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentFilterDTO;
import com.example.dto.comment.CommentUpdateDTO;
import com.example.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/create")
    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentCreateDTO dto) {
        return ResponseEntity.ok(commentService.create(dto));
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDTO> update(@PathVariable String commentId,
                                             @Valid @RequestBody CommentUpdateDTO dto) {
        return ResponseEntity.ok(commentService.update(commentId, dto));

    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Boolean> delete(@PathVariable String commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok(true);
    }


    @GetMapping("/getAllByArticleId/{articleId}")
    public ResponseEntity<List<CommentDTO>> getAllByArticleId(@PathVariable String articleId) {
        return ResponseEntity.ok(commentService.getAllByArticleId(articleId));
    }


    @GetMapping("/paginationWithArticleId/{articleId}")
    public ResponseEntity<Page<CommentDTO>> paginationWithArticleId(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                                                    @RequestParam(name = "size", defaultValue = "3") int pageSize,
                                                                    @PathVariable String articleId) {
        return ResponseEntity.ok(commentService.paginationWithArticleId(pageNumber - 1, pageSize, articleId));

    }

    @PostMapping("/filter")
    public ResponseEntity<Page<CommentDTO>> filter(@RequestBody CommentFilterDTO commentFilterDTO,
                                                   @RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                                   @RequestParam(name = "size", defaultValue = "3") int pageSize) {
        Page<CommentDTO> dtoPage = commentService.filter(commentFilterDTO, pageNumber - 1, pageSize);
        return ResponseEntity.ok(dtoPage);

    }

    @GetMapping("/replyList/{commentId}")
    public ResponseEntity<List<CommentDTO>> getAllByReplyId(@PathVariable String commentId) {
        List<CommentDTO> response = commentService.getAllByReplyId(commentId);
        return ResponseEntity.ok(response);
    }

}
