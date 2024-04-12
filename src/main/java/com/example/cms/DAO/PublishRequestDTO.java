package com.example.cms.DAO;

import jakarta.validation.constraints.NotNull;

public class PublishRequestDTO {

	@NotNull(message = "SEO Titlte Should not Be Null")
	private String seoTitle;
	private String seoDescription;
	private String[] seoTags;
	private ScheduleRequestDTO schedule;
	
	
	
	public ScheduleRequestDTO getSchedule() {
		return schedule;
	}
	public void setSchedule(ScheduleRequestDTO schedule) {
		this.schedule = schedule;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public String[] getSeoTags() {
		return seoTags;
	}
	public void setSeoTags(String[] seoTags) {
		this.seoTags = seoTags;
	}
}
