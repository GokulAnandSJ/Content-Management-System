package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.DAO.BlogRequestDTO;
import com.example.cms.DAO.BlogResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResponse>> createBlog(BlogRequestDTO blogRequestDTO ,int userId);
	
	ResponseEntity<ResponseStructure<Boolean>> checkForBlogTitleAvailability(String title);
	
	ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId);
	
	ResponseEntity<ResponseStructure<BlogResponse>> ubdateBlogData(int blogId , BlogRequestDTO blogRequestDto);
}
