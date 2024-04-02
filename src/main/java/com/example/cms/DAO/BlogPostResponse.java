package com.example.cms.DAO;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.stereotype.Component;

import com.example.cms.Enums.PostType;


@Component
public class BlogPostResponse {

	private int postId;
	private String title;
	private String subTitle;
	private String summary;
	private PostType postType;
//	private String seoTitle;
//	private String seoDescription;
//	private String[] seoTopics;
	private LocalDate createdAt;
	private LocalDate lastModifiedAt;
	
	@CreatedBy
	private String createdBy;
	@LastModifiedBy
	private String lastModifiedBy;
	
	
	public int getPostId() {
		return postId;
	}
	public BlogPostResponse setPostId(int postId) {
		this.postId = postId;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public BlogPostResponse setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public BlogPostResponse setSubTitle(String subTitle) {
		this.subTitle = subTitle;
		return this;
	}
	public String getSummary() {
		return summary;
	}
	public BlogPostResponse setSummary(String summary) {
		this.summary = summary;
		return this;
	}
	public PostType getPostType() {
		return postType;
	}
	public BlogPostResponse setPostType(PostType postType) {
		this.postType = postType;
		return this;
	}
//	public String getSeoTitle() {
//		return seoTitle;
//	}
//	public BlogPostResponse setSeoTitle(String seoTitle) {
//		this.seoTitle = seoTitle;
//		return this;
//	}
//	public String getSeoDescription() {
//		return seoDescription;
//	}
//	public BlogPostResponse setSeoDescription(String seoDescription) {
//		this.seoDescription = seoDescription;
//		return this;
//	}
//	public String[] getSeoTopics() {
//		return seoTopics;
//	}
//	public BlogPostResponse setSeoTopics(String[] seoTopics) {
//		this.seoTopics = seoTopics;
//		return this;
//	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public BlogPostResponse setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
		return this;
	}
	public LocalDate getLastModifiedAt() {
		return lastModifiedAt;
	}
	public BlogPostResponse setLastModifiedAt(LocalDate lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
		return this;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public BlogPostResponse setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public BlogPostResponse setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
		return this;
	}
	
	
}
