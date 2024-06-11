package com.example.repository;

import com.example.dto.FilterResponseDTO;
import com.example.dto.article.ArticleFilterDTO;
import com.example.entity.ArticleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResponseDTO<ArticleEntity> filter(ArticleFilterDTO dto, Integer pageNumber, Integer pageSize){
        StringBuilder query = new StringBuilder();
        Map<String,Object> params = new HashMap<>();

        if (dto.getId()!=null){
            query.append(" AND a.id=:id ");
            params.put("id", dto.getId());
        }
        if (dto.getTitle()!=null){
            query.append(" AND a.title = :title ");
            params.put("title", dto.getTitle());
        }
        if (dto.getRegionId()!=null){
            query.append(" AND a.region_id = :regionId ");
            params.put("regionId", dto.getId());
        }
        if (dto.getCategoryId()!=null){
            query.append(" AND a.category_id = :categoryId ");
            params.put("categoryId", dto.getCategoryId());
        }
        if (dto.getCreatedDateFrom()!=null){
            query.append(" AND a.created_date >= :createdDateFrom ");
            params.put("createdDateFrom", dto.getCreatedDateFrom());
        }
        if (dto.getCreatedDateTo()!=null){
            query.append(" AND a.created_date <= :createdDateTo ");
            params.put("createdDateTo", dto.getCreatedDateTo());
        }
        if (dto.getPublishedDateFrom()!=null){
            query.append(" AND a.publish_date >= :publishedDateFrom ");
            params.put("publishedDateFrom", dto.getPublishedDateFrom());
        }
        if (dto.getPublishedDateTo()!=null){
            query.append(" AND a.publish_date <= :createdDateTo ");
            params.put("createdDateTo", dto.getPublishedDateTo());
        }
        if (dto.getPublisherId()!=null){
            query.append(" AND a.publisher_id = :publisherId ");
            params.put("publisherId", dto.getPublisherId());
        }
        if (dto.getModeratorId()!=null){
            query.append(" AND a.moderator_id = :moderatorId ");
            params.put("moderatorId", dto.getModeratorId());
        }
        if (dto.getStatus()!=null){
            query.append(" AND a.status = :status ");
            params.put("status", dto.getStatus());
        }

        StringBuilder selectSQL = new StringBuilder(" from ArticleEntity a where 1=1 ").append(query);
        StringBuilder countSQL = new StringBuilder("select count(*) from ArticleEntity a where 1=1 ").append(query);

        Query selectQuery = entityManager.createQuery(selectSQL.toString());
        Query countQuery = entityManager.createQuery(countSQL.toString());

        params.forEach((key,value)->{
            selectQuery.setParameter(key,value);
            countQuery.setParameter(key,value);
        });

        selectQuery.setFirstResult(pageNumber * pageSize);
        selectQuery.setMaxResults(pageSize);

        List<ArticleEntity> list = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResponseDTO<ArticleEntity>(list, totalCount);
    }
}
