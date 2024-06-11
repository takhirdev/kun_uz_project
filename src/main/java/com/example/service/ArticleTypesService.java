package com.example.service;

import com.example.dto.type.TypesDTO;
import com.example.entity.ArticleTypesEntity;
import com.example.repository.ArticleTypesRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleTypesService {
    @Autowired
    private ArticleTypesRepository articleTypesRepository;

    public void create(String articleId, List<Integer> typesList) {
        for (Integer typesId : typesList) {
            create(articleId, typesId);
        }
    }

    public void create(String articleId, Integer typesId) {
        ArticleTypesEntity entity = new ArticleTypesEntity();
        entity.setArticleId(articleId);
        entity.setTypesId(typesId);
        articleTypesRepository.save(entity);
    }

    public void merge(String articleId, List<Integer> newList) {
        Objects.requireNonNull(newList);

        List<Integer> oldLists = articleTypesRepository.findAllTypesIdByArticleId(articleId);

        oldLists.forEach(oldId -> {
            if (!newList.contains(oldId)) {
                articleTypesRepository.deleteByArticleIdAndTypesId(articleId, oldId);
            }
        });
        newList.forEach(newId -> {
            if (!oldLists.contains(newId)) {
                create(articleId, newId);
            }
        });
    }
}
