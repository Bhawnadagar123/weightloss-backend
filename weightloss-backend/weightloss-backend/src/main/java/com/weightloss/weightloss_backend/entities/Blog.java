package com.weightloss.weightloss_backend.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "blogs")
public class Blog {
    @Id
    private String id;

    private String title;
    private String slug; // optional SEO-friendly slug
    private String summary; // short description
    private String content; // markdown or html
    private String author;
    private String category;
    private List<String> tags;
    private boolean featured = false;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public Blog() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public Blog(String id, String title, String slug, String summary, String content, String author, String category,
			List<String> tags, boolean featured, Instant createdAt, Instant updatedAt) {
		super();
		this.id = id;
		this.title = title;
		this.slug = slug;
		this.summary = summary;
		this.content = content;
		this.author = author;
		this.category = category;
		this.tags = tags;
		this.featured = featured;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

    // getters & setters (or use Lombok @Data)
    // ... (generate in your IDE)
}

