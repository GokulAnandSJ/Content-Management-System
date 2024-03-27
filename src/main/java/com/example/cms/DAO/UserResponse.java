package com.example.cms.DAO;

import java.time.LocalDate;

import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;
import org.springframework.stereotype.Component;

@Component
public class UserResponse {

	private int userId;
	private String userName;
	private String email;
	private LocalDate createdAt;
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public UserResponse  setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
		return this;
	}
	private LocalDate lastModifiedAt;
	
	public LocalDate getLastModifiedAt() {
		return lastModifiedAt;
	}
	public UserResponse  setLastModifiedAt(LocalDate lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
		return this;
	}
	public int getUserId() {
		return userId;
	}
	public UserResponse setUserId(int userId) {
		this.userId = userId;
		return this;
	}
	public String getUserName() {
		return userName;
	}
	public UserResponse setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public UserResponse setEmail(String email) {
		this.email = email;
		return this;
	}
	
}
