package com.example.service;

import com.example.dto.FilterResponseDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.comment.CommentCreateDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentFilterDTO;
import com.example.dto.comment.CommentUpdateDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.exception.AppBadException;
import com.example.repository.CommentCustomRepository;
import com.example.repository.CommentRepository;
import com.example.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentCustomRepository commentCustomRepository;

    public CommentService(CommentRepository commentRepository, CommentCustomRepository commentCustomRepository) {
        this.commentRepository = commentRepository;
        this.commentCustomRepository = commentCustomRepository;
    }

    public CommentDTO create(CommentCreateDTO dto) {
        CommentEntity saved = commentRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public CommentDTO update(String commentId, CommentUpdateDTO comment) {
        Integer profileId = SecurityUtil.getProfileId();

        CommentEntity commentEntity = getById(commentId);
        if (!profileId.equals(commentEntity.getProfileId())) {
            throw new AppBadException("You can only update your comment");
        }

        commentEntity.setContent(comment.getContent());
        commentEntity.setUpdateDate(LocalDateTime.now());
        commentRepository.save(commentEntity);
        return toDTO(commentEntity);
    }

    public Boolean delete(String commentId) {
        CommentEntity entity = getById(commentId);
        ProfileEntity profile = SecurityUtil.getProfile();

        if (profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            return deleteAsAdmin(commentId);
        } else {
            return deleteAsUser(commentId, entity);
        }
    }

    public List<CommentDTO> getAllByArticleId(String articleId) {
        return commentRepository.findByArticleIdAndVisibleTrue(articleId).stream()
                .map(entity -> {
                    CommentDTO dto = new CommentDTO();
                    dto.setId(entity.getId());
                    dto.setContent(entity.getContent());
                    dto.setCreatedDate(entity.getCreatedDate());
                    dto.setUpdateDate(entity.getUpdateDate());

                    // create profile
                    ProfileDTO profile = new ProfileDTO();
                    profile.setId(entity.getProfileId());
                    profile.setName(entity.getProfile().getName());
                    profile.setSurname(entity.getProfile().getSurname());
                    dto.setProfile(profile);
                    return dto;
                })
                .toList();
    }

    public Page<CommentDTO> paginationWithArticleId(int page, int size, String articleId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> commentEntityPage = commentRepository.findByArticleIdAndVisibleTrue(articleId, pageable);

        List<CommentDTO> comments = commentEntityPage.getContent()
                .stream()
                .map(this::toDTO)
                .toList();

        Long total = commentEntityPage.getTotalElements();

        return new PageImpl<>(comments, pageable, total);
    }

    public Page<CommentDTO> filter(CommentFilterDTO filterDTO, int page, int size) {
        FilterResponseDTO<CommentEntity> filter = commentCustomRepository.filter(filterDTO, page, size);
        List<CommentDTO> dtoList = filter.getContent()
                .stream()
                .map(entity -> {
                    CommentDTO dto = new CommentDTO();
                    dto.setId(entity.getId());
                    dto.setCreatedDate(entity.getCreatedDate());
                    dto.setUpdateDate(entity.getUpdateDate());
                    dto.setContent(entity.getContent());
                    dto.setReplyId(entity.getReplyId());
                    dto.setVisible(entity.getVisible());

                    // create profile
                    ProfileDTO profileDTO = new ProfileDTO();
                    profileDTO.setId(entity.getProfileId());
                    dto.setProfile(profileDTO);

                    // create article
                    ArticleDTO articleDTO = new ArticleDTO();
                    articleDTO.setId(entity.getArticleId());
                    dto.setArticle(articleDTO);
                    return dto;
                }).toList();
        return new PageImpl<>(dtoList, PageRequest.of(page, size), filter.getTotalCount());
    }

    public List<CommentDTO> getAllByReplyId(String commentId) {
        return commentRepository.findAllByReplyIdAndVisibleTrue(commentId).stream()
                .map(entity -> {
                    CommentDTO dto = new CommentDTO();
                    dto.setId(entity.getId());
                    dto.setCreatedDate(entity.getCreatedDate());
                    dto.setUpdateDate(entity.getUpdateDate());
                    dto.setContent(entity.getContent());
                    dto.setReplyId(entity.getReplyId());
                    dto.setVisible(entity.getVisible());

                    // create profile
                    ProfileDTO profileDTO = new ProfileDTO();
                    profileDTO.setId(entity.getProfileId());
                    dto.setProfile(profileDTO);

                    // create article
                    ArticleDTO articleDTO = new ArticleDTO();
                    articleDTO.setId(entity.getArticleId());
                    dto.setArticle(articleDTO);
                    return dto;
                })
                .toList();
    }

    public CommentEntity getById(String commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new AppBadException("Comment not found"));
    }

    private CommentEntity toEntity(CommentCreateDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        return entity;
    }

    private CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setContent(entity.getContent());
        dto.setArticleId(entity.getArticleId());
        dto.setReplyId(entity.getReplyId());
        dto.setProfileId(entity.getProfileId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private Boolean deleteAsAdmin(String commentId) {
        commentRepository.deleteById(commentId);
        return true;
    }

    private Boolean deleteAsUser(String commentId, CommentEntity entity) {
        if (!SecurityUtil.getProfileId().equals(entity.getProfileId())) {
            throw new AppBadException("You can only delete your comment");
        }
        commentRepository.deleteById(commentId);
        return true;
    }
}
