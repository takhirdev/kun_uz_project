package com.example.entity;

import com.example.enums.Reaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable = false, updatable = false)
    private ProfileEntity profile;
    @Column(name = "profile_id")
    private Integer profileId;

    @Column(name = "comment_id")
    private String commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id",insertable = false, updatable = false)
    private CommentEntity comment;

    private LocalDateTime createdDate = LocalDateTime.now();

    private Reaction status;
}
