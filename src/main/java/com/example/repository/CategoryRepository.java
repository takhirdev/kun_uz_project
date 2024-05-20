package com.example.repository;

import com.example.entity.CategoryEntity;
import com.example.mapper.CategoryMapper;
import com.example.mapper.RegionMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    @Query(value = " select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'EN' THEN name_en " +
            "   WHEN 'RU' THEN name_ru " +
            "  END as name " +
            "from category order by order_number desc; ", nativeQuery = true)
    List<CategoryMapper> findAllByLang(@Param("lang") String lang);
}
