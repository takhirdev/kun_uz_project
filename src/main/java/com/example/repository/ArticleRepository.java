package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.mapper.ArticleShortInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {

    Optional<ArticleEntity> findByIdAndVisibleTrue(String id);

    @Query( " SELECT a.id, a.title, a.description, a.image, a.publishedDate" +
            " FROM ArticleEntity AS a " +
            " WHERE a.id NOT IN ?1 " +
            " AND a.visible = TRUE AND a.status = 'PUBLISHED' " +
            " ORDER BY a.createdDate DESC " +
            " LIMIT 8 ")
    List<ArticleShortInfoMapper> getLast8(List <String> list);

    @Query( " SELECT a.id, a.title, a.description, a.image, a.publishedDate " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.articleTypes AS at " +
            " WHERE  at.typesId = ?1 " +
            " AND a.status = 'PUBLISHED' " +
            " AND a.visible = TRUE" +
            " ORDER BY a.createdDate DESC " +
            " LIMIT ?2 ")
    List<ArticleShortInfoMapper> getLastNArticleByType(Integer typeId, Integer limit);

    @Query( " SELECT a FROM ArticleEntity AS a " +
            " INNER JOIN a.articleTypes AS at" +
            " WHERE at.typesId = ?1 " +
            " AND a.id <> ?2 " +
            " AND a.status = 'PUBLISHED'" +
            " AND a.visible = TRUE " +
            " ORDER BY a.publishedDate " +
            " LIMIT 4 ")
    List<ArticleShortInfoMapper> getLast4ArticlesByTypeExcludingId(Integer typeId, Integer articleId);

    @Query( " SELECT a.id, a.title, a.description, a.image, a.publishedDate" +
            " FROM ArticleEntity AS a " +
            " WHERE a.visible = TRUE" +
            " AND a.status = 'PUBLISHED' " +
            " ORDER BY a.viewsCount DESC " +
            " LIMIT 4 ")
    List<ArticleShortInfoMapper> getMostRead4Article();

    @Query( " SELECT a.id, a.title, a.description, a.image, a.publishedDate " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.articleTypes AS at " +
            " WHERE at.id = ?1" +
            " AND a.regionId = ?2 " +
            " AND a.visible = TRUE" +
            " AND a.status = 'PUBLISHED' " +
            " ORDER BY a.createdDate DESC " +
            " LIMIT 5 ")
    List<ArticleShortInfoMapper> get5ArticleByTypeIdAndRegionId(Integer typeId, Integer regionId);

    @Query( " SELECT a.id, a.title, a.description, a.image, a.publishedDate" +
            " FROM ArticleEntity AS a " +
            " WHERE a.regionId = ?1 " +
            " AND a.status = 'PUBLISHED'" +
            " AND a.visible = TRUE " +
            " ORDER BY a.createdDate DESC "
            )
    List<ArticleShortInfoMapper> paginationByRegion(Integer regionId, Pageable pageable);

    @Query( " SELECT a.id, a.title, a.description, a.image, a.publishedDate " +
            " FROM ArticleEntity AS a " +
            " WHERE a.categoryId = ?1 " +
            " AND a.status = 'PUBLISHED' " +
            " AND a.visible = TRUE " +
            " ORDER BY a.createdDate DESC " +
            " LIMIT 5 ")
    List<ArticleShortInfoMapper> get5ArticleByCategoryId(Integer categoryId);

    @Query( " SELECT a.id, a.title, a.description, a.image, a.publishedDate" +
            " FROM ArticleEntity AS a " +
            " WHERE a.categoryId = ?1 " +
            " AND a.status = 'PUBLISHED'" +
            " AND a.visible = TRUE " +
            " ORDER BY a.createdDate DESC "
    )
    List<ArticleShortInfoMapper> paginationByCategory(Integer categoryId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE ArticleEntity set viewsCount = COALESCE(viewsCount,0) + 1 where id =?1")
    void increaseViewCount(String articleId);

    @Transactional
    @Modifying
    @Query("UPDATE ArticleEntity set sharedCount = COALESCE(sharedCount,0) + 1 where id =?1")
    void increaseShareCount(String articleId);
}

