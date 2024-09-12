package com.travelblog.comment_service.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "comment", indexes = {
        @Index(columnList = "post_id"),
        @Index(columnList = "author_id")
})
@Data
@NoArgsConstructor
public class CommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    private String content;
}
