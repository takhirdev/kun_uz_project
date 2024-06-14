package com.example.service;

import com.example.dto.article.ArticleLikeDTO;
import com.example.entity.ArticleLikeEntity;
import com.example.enums.Reaction;
import com.example.repository.ArticleLikeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;

    public ArticleLikeService(ArticleLikeRepository articleLikeRepository) {
        this.articleLikeRepository = articleLikeRepository;
    }

    public void reaction(Integer profileId, ArticleLikeDTO dto) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(dto.getArticleId(), profileId);

        if (optional.isEmpty()) {
            ArticleLikeEntity articleLikeEntity = new ArticleLikeEntity();
            articleLikeEntity.setArticleId(dto.getArticleId());
            articleLikeEntity.setProfileId(profileId);
            articleLikeEntity.setReaction(dto.getReaction());
            articleLikeRepository.save(articleLikeEntity);
            return;
        }

        ArticleLikeEntity articleLikeEntity = optional.get();
        if (articleLikeEntity.getReaction().equals(dto.getReaction())) {
            articleLikeRepository.delete(articleLikeEntity);
            return;
        }

        articleLikeEntity.setReaction(dto.getReaction());
        articleLikeRepository.save(articleLikeEntity);
    }
}