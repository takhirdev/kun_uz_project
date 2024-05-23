package com.example.repository;

import com.example.dto.FilterResponseDTO;
import com.example.dto.profile.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public FilterResponseDTO<ProfileEntity> filter(ProfileFilterDTO dto, Integer pageNumber, Integer pageSize) {

        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (dto.getName() != null) {
            query.append(" and p.name = :name ");
            params.put("name", dto.getName());
        }
        if (dto.getSurname() != null) {
            query.append(" and p.surname = :surname ");
            params.put("surname", dto.getSurname());
        }
        if (dto.getPhone() != null) {
            query.append(" and p.phone = :phone ");
            params.put("phone", dto.getPhone());
        }
        if (dto.getProfileRole() != null) {
            query.append(" and p.role = :role ");
            params.put("role", dto.getProfileRole());
        }
        if (dto.getCreatedDateFrom() != null) {
            query.append(" and p.createdDate >= :createdDateFrom ");
            params.put("createdDateFrom", dto.getCreatedDateFrom());
        }
        if (dto.getCreatedDateTo() != null) {
            query.append(" and p.createdDate <= :createdDateTo ");
            params.put("createdDateTo", dto.getCreatedDateTo());
        }

        StringBuilder selectSQL = new StringBuilder(" from ProfileEntity as p where visible = true ").append(query);
        StringBuilder countSQL = new StringBuilder(" select count(p) from ProfileEntity as p where visible = true ").append(query);

        Query selectQuery = entityManager.createQuery(selectSQL.toString());
        Query countQuery = entityManager.createQuery(countSQL.toString());

        params.forEach((key, value) -> {
            selectQuery.setParameter(key, value);
            countQuery.setParameter(key, value);
        });

        selectQuery.setFirstResult(pageNumber * pageSize);
        selectQuery.setMaxResults(pageSize);

        List<ProfileEntity> list = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();


        return new FilterResponseDTO<ProfileEntity>(list,totalCount);
    }
}
