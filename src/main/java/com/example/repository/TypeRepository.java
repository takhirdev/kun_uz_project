package com.example.repository;

import com.example.entity.TypesEntity;
import com.example.mapper.Mapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeRepository extends CrudRepository<TypesEntity,Integer> {
    @Query(value = " select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'EN' THEN name_en " +
            "   WHEN 'RU' THEN name_ru " +
            "  END as name " +
            "from article_types order by order_number desc; ", nativeQuery = true)
    List<Mapper> findAllByLang(@Param("lang") String lang);
}
