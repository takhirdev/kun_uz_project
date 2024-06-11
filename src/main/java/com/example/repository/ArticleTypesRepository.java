package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTypesRepository extends CrudRepository<ArticleTypesEntity, Integer> {

    @Query("Select a.typesId from ArticleTypesEntity as a where a.articleId =?1 ")
    List<Integer> findAllTypesIdByArticleId(String articleId);

    @Modifying
    @Transactional
    @Query("delete from ArticleTypesEntity  where articleId =?1 and typesId =?2")
    void deleteByArticleIdAndTypesId(String articleId, Integer typesId);


    @Query(value = " SELECT a FROM ArticleTypesEntity AS at " +
            " INNER JOIN at.article AS a" +
            " WHERE at.typesId = :typeId " +
            " AND a.status = 'PUBLISHED'" +
            " AND a.visible = true" +
            " ORDER BY a.createdDate ")
    List<ArticleEntity> getLastNArticleByTypeId(@Param("typeId") Integer typeId,
                                                Pageable pageable);


    @Query(" SELECT a FROM ArticleTypesEntity as at " +
            " INNER JOIN at.article as a" +
            " WHERE at.typesId = : typeId" +
            " AND a.id <> : articleId" +
            " AND a.status = 'PUBLISHED'" +
            " AND a.visible = true " +
            " ORDER BY a.publishedDate")
    List<ArticleEntity> getLastNArticleByTypeIdExceptArticleId(@Param("typeId") Integer typeId,
                                                               @Param("articleId") Integer articleId,
                                                               Pageable pageable);
}
