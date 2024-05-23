package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer> {
    List<EmailHistoryEntity> findAllByEmail(String email);
    @Query(value = "select * from email_history  where created_date between :from and :to ", nativeQuery = true)
    List<EmailHistoryEntity> findAllByGivenDate(LocalDate from, LocalDate to);
    Page<EmailHistoryEntity> findAllBy(Pageable pageable);
}
