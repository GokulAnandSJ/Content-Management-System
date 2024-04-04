package com.example.cms.entity;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String userName;
	private String email;
	private String password;
	@CreatedDate
	@Column(updatable = false)
	private LocalDate createdAt;
	@LastModifiedDate
	private LocalDate lastModifiedAt;
	@Column( columnDefinition = "boolean default false")
    private boolean deleteUser  ;
	
	@OneToMany(mappedBy = "user")
	private List<Blog> blogs;
	
	public List<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
	public boolean isDeleteUser() {
		return deleteUser;
	}
	public void setDeleteUser(boolean deleteUsre) {
		this.deleteUser = deleteUsre;
	}
	
	
	
}
