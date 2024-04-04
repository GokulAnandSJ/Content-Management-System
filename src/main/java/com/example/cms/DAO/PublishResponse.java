package com.example.cms.DAO;

import org.springframework.stereotype.Component;
import com.example.cms.Enums.PostType;

@Component
public class PublishResponse {

	private int publishId;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTags;
	
	
	public int getPublishId() {
		return publishId;
	}
	public PublishResponse setPublishId(int publishId) {
		this.publishId = publishId;
		return this;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public PublishResponse setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
		return this;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public PublishResponse setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
		return this;
	}
	public String[] getSeoTags() {
		return seoTags;
	}
	public PublishResponse setSeoTags(String[] seoTags) {
		this.seoTags = seoTags;
		return this;
	}
}