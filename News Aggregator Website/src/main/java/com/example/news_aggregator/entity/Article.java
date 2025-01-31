package com.example.news_aggregator.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "CLOB")
    private String content;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;
}