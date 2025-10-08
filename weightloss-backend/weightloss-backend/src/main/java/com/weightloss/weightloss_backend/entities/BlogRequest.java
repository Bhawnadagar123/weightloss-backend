package com.weightloss.weightloss_backend.entities;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class BlogRequest {
	
	 @NotBlank
	 private String title;
	 private String slug;
	 private String summary;
	 @NotBlank
	 private String content;
	 private String author;
	 private String category;
	 private List<String> tags;
	 private boolean featured;
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
	 public BlogRequest(@NotBlank String title, String slug, String summary, @NotBlank String content, String author,
			String category, List<String> tags, boolean featured) {
		super();
		this.title = title;
		this.slug = slug;
		this.summary = summary;
		this.content = content;
		this.author = author;
		this.category = category;
		this.tags = tags;
		this.featured = featured;
	 }
	 public BlogRequest() {
		super();
		// TODO Auto-generated constructor stub
	 }
}
