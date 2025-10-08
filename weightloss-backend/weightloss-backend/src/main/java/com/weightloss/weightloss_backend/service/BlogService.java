package com.weightloss.weightloss_backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.weightloss.weightloss_backend.entities.Blog;
import com.weightloss.weightloss_backend.entities.BlogRequest;
import com.weightloss.weightloss_backend.repository.BlogRepository;

import java.time.Instant;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository repo;

    public BlogService(BlogRepository repo) {
        this.repo = repo;
    }

    public Blog createBlog(BlogRequest req) {
        Blog b = new Blog();
        b.setTitle(req.getTitle());
        b.setSlug((req.getSlug() == null || req.getSlug().isBlank()) ? generateSlug(req.getTitle()) : req.getSlug());
        b.setSummary(req.getSummary());
        b.setContent(req.getContent());
        b.setAuthor(req.getAuthor());
        b.setCategory(req.getCategory());
        b.setTags(req.getTags());
        b.setFeatured(req.isFeatured());
        b.setCreatedAt(Instant.now());
        b.setUpdatedAt(Instant.now());
        return repo.save(b);
    }

    public Blog updateBlog(String id, BlogRequest req) {
        return repo.findById(id).map(b -> {
            b.setTitle(req.getTitle());
            b.setSlug((req.getSlug() == null || req.getSlug().isBlank()) ? generateSlug(req.getTitle()) : req.getSlug());
            b.setSummary(req.getSummary());
            b.setContent(req.getContent());
            b.setAuthor(req.getAuthor());
            b.setCategory(req.getCategory());
            b.setTags(req.getTags());
            b.setFeatured(req.isFeatured());
            b.setUpdatedAt(Instant.now());
            return repo.save(b);
        }).orElseThrow(() -> new IllegalArgumentException("Blog not found"));
    }

    public void deleteBlog(String id) {
        repo.deleteById(id);
    }

    public Blog getById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Blog not found"));
    }

    public Page<Blog> listAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Blog> listByCategory(String category, Pageable pageable) {
        return repo.findByCategoryIgnoreCase(category, pageable);
    }

    public Page<Blog> search(String q, Pageable pageable) {
        return repo.findByTitleContainingIgnoreCaseOrSummaryContainingIgnoreCase(q, q, pageable);
    }

    public List<Blog> featured() {
        return repo.findByFeaturedTrueOrderByCreatedAtDesc();
    }

    private String generateSlug(String title) {
        return title.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().replaceAll("\\s+", "-");
    }
}
