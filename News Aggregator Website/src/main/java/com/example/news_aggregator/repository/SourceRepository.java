package com.example.news_aggregator.repository;

import com.example.news_aggregator.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long>, PagingAndSortingRepository<Source, Long> {
}
