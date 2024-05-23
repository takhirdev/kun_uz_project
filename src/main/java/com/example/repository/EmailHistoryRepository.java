package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer> {
    List<EmailHistoryEntity> findAllByEmail(String email);
    List<EmailHistoryEntity> findAllByCreatedDate(LocalDate date);
    Page<EmailHistoryEntity> findAllBy(Pageable pageable);
}
