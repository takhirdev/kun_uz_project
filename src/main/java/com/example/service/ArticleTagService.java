package com.example.service;

import com.example.entity.ArticleTagEntity;
import com.example.repository.ArticleTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleTagService {
    private final  ArticleTagRepository articleTagRepository;

    public ArticleTagService(ArticleTagRepository articleTagRepository) {
        this.articleTagRepository = articleTagRepository;
    }

    public void create(String articleId, List<String> tagList) {
        Objects.requireNonNull(tagList);
        List<Long> oldLists = articleTagRepository.findAllTypesIdByArticleId(articleId);
        tagList.forEach(tagId -> {
            if (!oldLists.contains(tagId)) {
                create(articleId, tagId);
            }
        });
    }

    public void create(String articleId, String tagId) {
        ArticleTagEntity entity = new ArticleTagEntity();
        entity.setArticleId(articleId);
        entity.setTagId(tagId);
        articleTagRepository.save(entity);
    }

    public void merge(String articleId, List<String> newList) {
        Objects.requireNonNull(newList);

        List<Long> oldLists = articleTagRepository.findAllTypesIdByArticleId(articleId);
        oldLists.forEach(oldId -> {
            if (!newList.contains(oldId)) {
                articleTagRepository.deleteByArticleIdAndTypesId(articleId, oldId);
            }
        });
        newList.forEach(newId -> {
            if (!oldLists.contains(newId)) {
                create(articleId, newId);
            }
        });
    }
}
