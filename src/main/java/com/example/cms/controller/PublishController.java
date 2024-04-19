package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.DAO.BlogPostResponse;
import com.example.cms.DAO.PublishRequestDTO;
import com.example.cms.DAO.PublishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
public class PublishController {

	private PublishService publishService;

	
	public PublishController(PublishService publishService) {
		super();
		this.publishService = publishService;
	}


	@Operation(description = "this endpoit  is used to create a PublishBlogPost On Publish" , responses = {
			@ApiResponse (responseCode = "200", description = "PublishBlogPost is Created "),
			@ApiResponse(responseCode = "400", description = "Invalid inputs", content =@Content(schema = @Schema(implementation = ErrorStructure.class))),
			@ApiResponse(responseCode = "404", description = "Publish Not Found", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })

      @PostMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(@RequestBody @Valid PublishRequestDTO publishRequestDTO , @PathVariable int postId) {
		return publishService.publishBlogPost(publishRequestDTO, postId);
	}
	

	@Operation(description = "this endpoit  is used to unpublished a Post Based On postId", responses = {
			@ApiResponse(responseCode = "200", description = "Post is unpublished "),
			@ApiResponse(responseCode = "404", description = "post is not found inputs", content = @Content(schema = @Schema(implementation = ErrorStructure.class))),
			@ApiResponse(responseCode = "404", description = "Unautherized Operation", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })
	@PutMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unpublishBlogPost(@PathVariable int postId) {
		return publishService.unpublishedBlogPost(postId);
	}
	
}
