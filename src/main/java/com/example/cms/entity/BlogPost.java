package com.example.cms.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.cms.Enums.PostType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class BlogPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	private String title;
	private String subTitle;
	@NotNull
	@Column(length = 3000)
	@Size(min = 500, max =3000, message = "Summary Contain at least 500 Character")
	private String summary;
	private PostType postType;
//	private String seoTitle;
//	private String seoDescription;
//	private String[] seoTopics;
	@CreatedDate
	@Column(updatable = false)
	private LocalDate createdAt;
	@LastModifiedDate
	private LocalDate lastModifiedAt;
	
	@CreatedBy
	private String createdBy;
	@LastModifiedBy
	private String lastModifiedBy;
	
	
	@ManyToOne
	private Blog blog;
	
	
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public PostType getPostType() {
		return postType;
	}
	public void setPostType(PostType postType) {
		this.postType = postType;
	}
//	public String getSeoTitle() {
//		return seoTitle;
//	}
//	public void setSeoTitle(String seoTitle) {
//		this.seoTitle = seoTitle;
//	}
//	public String getSeoDescription() {
//		return seoDescription;
//	}
//	public void setSeoDescription(String seoDescription) {
//		this.seoDescription = seoDescription;
//	}
//	public String[] getSeoTopics() {
//		return seoTopics;
//	}
//	public void setSeoTopics(String[] seoTopics) {
//		this.seoTopics = seoTopics;
//	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDate getLastModifiedAt() {
		return lastModifiedAt;
	}
	public void setLastModifiedAt(LocalDate lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
}
