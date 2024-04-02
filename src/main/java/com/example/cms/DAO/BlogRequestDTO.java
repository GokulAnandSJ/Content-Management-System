 package com.example.cms.DAO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class BlogRequestDTO {

	@NotNull
    @Pattern(regexp = "^[A-Za-z]+$", message = "Title should contain only alphabetical characters")
	private String title;
	private String[] topics;
	private String about;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getTopics() {
		return topics;
	}
	public void setTopics(String[] topics) {
		this.topics = topics;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	
	
	
}
