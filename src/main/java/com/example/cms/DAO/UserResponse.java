package com.example.cms.DAO;

import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;
import org.springframework.stereotype.Component;

@Component
public class UserResponse {

	private int userId;
	private String userName;
	private String email;
	
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
