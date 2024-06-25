package com.example.repository;

import com.example.entity.ArticleTagEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTagRepository extends CrudRepository<ArticleTagEntity,Long> {

    @Query("select a.tagId from ArticleTagEntity as a where a.articleId =?1 ")
    List<Long> findAllTypesIdByArticleId(String articleId);

    @Modifying
    @Transactional
    @Query("delete from ArticleTagEntity  where articleId =?1 and tagId =?2")
    void deleteByArticleIdAndTypesId(String articleId, Long tagId);
}
