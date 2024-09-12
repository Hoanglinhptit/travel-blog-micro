package com.travelblog.post_service.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "post", indexes = {
        @Index(columnList = "author_id"),
        @Index(columnList = "status")
})public class PostEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "posted_at", nullable = false)
    private LocalDateTime postedAt = LocalDateTime.now();

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @ElementCollection
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag_id")
    private List<Integer> tagIds;

    // Store a list of tag IDs to communicate with tag-service
    @ElementCollection
    @CollectionTable(name = "post_comments", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "comment_id")
    private List<Integer> commentIds;  // Reference to tag IDs from tag-service

    @ElementCollection
    @CollectionTable(name = "post_categories", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "category_id")
    private List<Integer> categoryIds;  // Reference to category IDs from category-service

    // Author ID to be fetched from user-service
    @Column(name = "author_id", nullable = false)
    private Integer authorId;  // Reference to author ID from user-service

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private Integer views = 1;

}
