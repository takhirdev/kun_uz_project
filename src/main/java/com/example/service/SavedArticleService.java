package com.example.service;

import com.example.dto.AttachDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.SavedArticleDTO;
import com.example.entity.AttachEntity;
import com.example.entity.SavedArticleEntity;
import com.example.exception.AppBadException;
import com.example.repository.SavedArticleRepository;
import com.example.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SavedArticleService {
    private final SavedArticleRepository savedArticleRepository;
    private final AttachService attachService;

    public SavedArticleService(SavedArticleRepository savedArticleRepository, AttachService attachService) {
        this.savedArticleRepository = savedArticleRepository;
        this.attachService = attachService;
    }

    public Boolean save(String articleId) {
        Integer profileId = SecurityUtil.getProfileId();
        Optional<SavedArticleEntity> exists = savedArticleRepository.findByArticleIdAndProfileId(articleId, profileId);
        if (exists.isPresent()) {
            throw new AppBadException("This article already saved");
        }

        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(articleId);
        entity.setProfileId(profileId);
        SavedArticleEntity saved = savedArticleRepository.save(entity);
        return true;
    }

    public void delete(String articleId) {
        Integer profileId = SecurityUtil.getProfileId();
        SavedArticleEntity article = savedArticleRepository.findByArticleIdAndProfileId(articleId, profileId)
                .orElseThrow(() -> new AppBadException("This article does not exist"));
        savedArticleRepository.delete(article);
    }

    public List<SavedArticleDTO> getAll() {
        Integer profileId = SecurityUtil.getProfileId();
        List<SavedArticleEntity> savedList = savedArticleRepository.findAllByProfileId(profileId);

        return savedList.stream()
                .map(entity -> {

                    SavedArticleDTO dto = new SavedArticleDTO();
                    dto.setId(entity.getId());

                    // create article
                    ArticleDTO article = new ArticleDTO();
                    article.setId(entity.getArticleId());
                    article.setTitle(entity.getArticle().getTitle());
                    article.setDescription(entity.getArticle().getDescription());

                    AttachDTO image = attachService.getDTOWithURL(entity.getArticle().getImageId());
                    article.setImage(image);

                    dto.setArticle(article);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
