package com.example.cms.DAO;

import com.example.cms.Enums.PostType;

import jakarta.validation.constraints.NotNull;

public class BlogPostRequestDTO {

	@NotNull
	private String title;
	private String subTitle;
	private String summary;
	private PostType postType;
	
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
	
}
