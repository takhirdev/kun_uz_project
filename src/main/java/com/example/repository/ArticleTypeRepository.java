package com.example.repository;

import com.example.entity.ArticleTypesEntity;
import com.example.mapper.ArticleTypesMapper;
import com.example.mapper.CategoryMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypesEntity,Integer> {
    @Query(value = " select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'EN' THEN name_en " +
            "   WHEN 'RU' THEN name_ru " +
            "  END as name " +
            "from article_types order by order_number desc; ", nativeQuery = true)
    List<ArticleTypesMapper> findAllByLang(@Param("lang") String lang);
}
