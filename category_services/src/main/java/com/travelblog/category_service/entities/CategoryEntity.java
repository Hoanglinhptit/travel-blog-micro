package com.travelblog.category_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "category", indexes = {
        @Index(columnList = "name")
})
@NoArgsConstructor
public class CategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "category_posts", joinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "post_id")
    private List<Integer> postId;
}
