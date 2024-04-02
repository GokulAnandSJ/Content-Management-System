package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.DAO.BlogPostRequestDTO;
import com.example.cms.DAO.BlogPostResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(BlogPostRequestDTO blogPostRequest , int blogId);
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updatePost(BlogPostRequestDTO blogPostRequest, int postId);
	
	ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft( int postId);

//	ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId);
}
