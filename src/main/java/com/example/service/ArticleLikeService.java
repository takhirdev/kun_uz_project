package com.example.service;

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

    public void reaction(String articleId, Integer profileId, Reaction reaction) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(articleId, profileId);

        if (optional.isEmpty()) {
            ArticleLikeEntity articleLikeEntity = new ArticleLikeEntity();
            articleLikeEntity.setArticleId(articleId);
            articleLikeEntity.setProfileId(profileId);
            articleLikeEntity.setReaction(reaction);
            articleLikeRepository.save(articleLikeEntity);
            return;
        }

        ArticleLikeEntity articleLikeEntity = optional.get();
        if (articleLikeEntity.getReaction().equals(reaction)) {
            articleLikeRepository.delete(articleLikeEntity);
            return;
        }

        articleLikeEntity.setReaction(reaction);
        articleLikeRepository.save(articleLikeEntity);
    }
}