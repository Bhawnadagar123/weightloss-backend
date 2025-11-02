package com.weightloss.weightloss_backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.weightloss.weightloss_backend.entities.Blog;
import com.weightloss.weightloss_backend.entities.BlogRequest;
import com.weightloss.weightloss_backend.service.BlogService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin(origins = "http://localhost:4200") // allow Angular during dev
public class BlogController {

    private final BlogService svc;

    public BlogController(BlogService svc) {
        this.svc = svc;
    }

    // Create
    @PostMapping
    public ResponseEntity<Blog> createBlog(@Valid @RequestBody BlogRequest req) {
        Blog created = svc.createBlog(req);
        return ResponseEntity.ok(created);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable String id, @Valid @RequestBody BlogRequest req) {
        Blog updated = svc.updateBlog(id, req);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable String id) {
        svc.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    // Get single
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable String id) {
        return ResponseEntity.ok(svc.getById(id));
    }

    // List with pagination: /api/blogs?page=0&size=10
    @GetMapping
    public ResponseEntity<Page<Blog>> listBlogs(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Page<Blog> p = svc.listAll(PageRequest.of(page, size));
        return ResponseEntity.ok(p);
    }

    // Search: /api/blogs/search?q=weight&page=0&size=10
    @GetMapping("/search")
    public ResponseEntity<Page<Blog>> search(@RequestParam String q,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(svc.search(q, PageRequest.of(page, size)));
    }

    // By category: /api/blogs/category/{category}?page=0&size=10
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Blog>> byCategory(@PathVariable String category,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(svc.listByCategory(category, PageRequest.of(page, size)));
    }

    // Featured posts
    @GetMapping("/featured")
    public ResponseEntity<List<Blog>> featured() {
        return ResponseEntity.ok(svc.featured());
    }
}
