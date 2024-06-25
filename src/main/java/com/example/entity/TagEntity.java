package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity {
    @Id
    @UuidGenerator
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
