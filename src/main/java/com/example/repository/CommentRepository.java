package com.example.repository;

import com.example.entity.CategoryEntity;
import com.example.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, String> {
    List<CommentEntity> findByArticleIdAndVisibleTrue(String articleId);
    Page<CommentEntity> findByArticleIdAndVisibleTrue(String articleId, Pageable pageable);
    List<CommentEntity> findAllByReplyIdAndVisibleTrue(String articleId);
}
