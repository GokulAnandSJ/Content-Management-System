package com.example.cms.DAO;

import org.springframework.stereotype.Component;

@Component
public class BlogPostResponse {

	private int postId;
	private String title;
	private String subTitle;
	private String summary;
	private String createdBy;
	private PublishResponse publishResponse;
	
	public PublishResponse getPublishResponse() {
		return publishResponse;
	}
	public BlogPostResponse setPublishResponse(PublishResponse publishResponse) {
		this.publishResponse = publishResponse;
		return this;
	}
	
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
