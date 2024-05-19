package com.example.repository;

import com.example.mapper.RegionMapper;
import com.example.entity.RegionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionEntity,Integer> {
    @Query(value = " select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'EN' THEN name_en " +
            "   WHEN 'RU' THEN name_ru " +
            "  END as name " +
            "from region order by order_number desc; ", nativeQuery = true)
    List<RegionMapper> findAllByLang(@Param("lang") String lang);
}