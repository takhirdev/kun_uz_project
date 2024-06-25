package com.example.repository;

import com.example.entity.SavedArticleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity,Integer> {

    Optional<SavedArticleEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    List<SavedArticleEntity> findAllByProfileId(Integer profileId);
}
