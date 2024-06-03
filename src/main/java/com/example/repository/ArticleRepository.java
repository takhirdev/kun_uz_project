package com.example.repository;

import com.example.entity.ArticleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    @Query(value = " SELECT * FROM article AS a " +
                   " INNER JOIN article_types AS at ON a.id = at.article_id " +
                   " WHERE at.types_id = ?1" +
                   " ORDER BY a.created_date desc" +
                   " LIMIT 5 ", nativeQuery = true)
    List<ArticleEntity> getLast5ByType(Integer typeId);


    @Query(value = " SELECT * FROM article AS a " +
            " INNER JOIN article_types AS at ON a.id = at.article_id " +
            " WHERE at.types_id = ?1 " +
            " ORDER BY a.created_date desc" +
            " LIMIT 3 ", nativeQuery = true)
    List<ArticleEntity> getLast3ByType(Integer typeId);
}

