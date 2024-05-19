package com.example.repository;

import com.example.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Page<ProfileEntity> findAllByVisibleTrue(Pageable pageable);
}
