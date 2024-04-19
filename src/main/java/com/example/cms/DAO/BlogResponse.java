package com.example.cms.DAO;


import org.springframework.stereotype.Component;

@Component
public class BlogResponse {

	private int blogId;
	private String title;
	private String[] topics;
	private String about;

	public String[] getTopics() {
		return topics;
	}
	public BlogResponse setTopics(String[] topics) {
		this.topics = topics;
		return this;
	}
	public String getAbout() {
		return about;
	}
	public int getBlogId() {
		return blogId;
	}
	public BlogResponse setBlogId(int blogId) {
		this.blogId = blogId;
		return this;
	}
	public BlogResponse setAbout(String about) {
		this.about = about;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public BlogResponse setTitle(String title) {
		this.title = title;
		return this;
	}
}
