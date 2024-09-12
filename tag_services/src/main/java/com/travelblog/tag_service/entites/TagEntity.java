package com.travelblog.tag_service.entites;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "tag", indexes = {
        @Index(columnList = "name")
})
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "tags_post", joinColumns = @JoinColumn(name = "tag_id"))
    @Column(name = "post_id")
    private List<Integer> postId;
}
