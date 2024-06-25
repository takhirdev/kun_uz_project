package com.example.repository;

import com.example.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CategoryEntity, String> {
}
