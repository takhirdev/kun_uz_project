package com.example.repository;

import com.example.entity.CommentLikeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, String> {
    Optional<CommentLikeEntity> findByCommentIdAndProfileId(String commentId, Integer profileId);
}
