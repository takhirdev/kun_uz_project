package com.example.repository;

import com.example.entity.ArticleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {


    @Query("FROM ArticleEntity WHERE id NOT IN : list ORDER BY publishedDate limit 8")
    List<ArticleEntity> getLast8ArticleByIdNotInList(List <Integer> list);

}

