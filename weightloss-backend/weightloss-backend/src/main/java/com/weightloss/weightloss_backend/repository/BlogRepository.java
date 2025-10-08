package com.weightloss.weightloss_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.weightloss.weightloss_backend.entities.Blog;

public interface BlogRepository extends MongoRepository<Blog, String> {
    Page<Blog> findByCategoryIgnoreCase(String category, Pageable pageable);
    Page<Blog> findByTagsIn(List<String> tags, Pageable pageable);
    Page<Blog> findByTitleContainingIgnoreCaseOrSummaryContainingIgnoreCase(String title, String summary, Pageable pageable);
    List<Blog> findByFeaturedTrueOrderByCreatedAtDesc();
}
