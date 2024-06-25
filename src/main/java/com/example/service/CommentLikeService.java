package com.example.service;

import com.example.dto.comment.CommentLikeDTO;
import com.example.entity.CommentLikeEntity;
import com.example.repository.CommentLikeRepository;
import com.example.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeService(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

    public void reaction(CommentLikeDTO dto) {
        Integer profileId = SecurityUtil.getProfileId();
        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentIdAndProfileId(dto.getCommentId(), profileId);

        if (optional.isEmpty()) {
            CommentLikeEntity entity = new CommentLikeEntity();
            entity.setCommentId(dto.getCommentId());
            entity.setProfileId(profileId);
            entity.setStatus(dto.getReaction());
            commentLikeRepository.save(entity);
            return;
        }

        CommentLikeEntity entity = optional.get();
        if (entity.getStatus().equals(dto.getReaction())) {
            commentLikeRepository.delete(entity);
            return;
        }

        entity.setStatus(dto.getReaction());
        commentLikeRepository.save(entity);
    }
}
