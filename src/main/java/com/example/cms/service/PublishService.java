package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.DAO.BlogPostResponse;
import com.example.cms.DAO.PublishRequestDTO;
import com.example.cms.DAO.PublishResponse;
import com.example.cms.utility.ResponseStructure;

public interface PublishService {

	ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequestDTO publishRequestDTO,int blogPostId);

	 ResponseEntity<ResponseStructure<BlogPostResponse>> unpublishedBlogPost(int postId);

}
