package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.DAO.BlogPostRequestDTO;
import com.example.cms.DAO.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
public class BlogPostController {

	private BlogPostService blogPostService;

	public BlogPostController(BlogPostService blogPostService) {
		super();
		this.blogPostService = blogPostService;
	}

	@Operation(description = "this endpoit  is used to create a Post Based On Blog" , responses = {
			@ApiResponse (responseCode = "200", description = "Post is Created "),
			@ApiResponse(responseCode = "400", description = "invalid inputs", content =@Content(schema = @Schema(implementation = ErrorStructure.class))),
			@ApiResponse(responseCode = "404", description = "Blog Not Found", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })


	@PostMapping("/blogs/{blogId}/blog-posts")
	private ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(@Valid @RequestBody BlogPostRequestDTO blogPostRequestDTO, @PathVariable int blogId) {
		return blogPostService.createDraft(blogPostRequestDTO, blogId);
	}

	
	@Operation(description = "this endpoit  is used to update a Post Based On postId", responses = {
			@ApiResponse(responseCode = "200", description = "Post is Updated "),
			@ApiResponse(responseCode = "400", description = "invalid inputs", content = @Content(schema = @Schema(implementation = ErrorStructure.class))),
			@ApiResponse(responseCode = "404", description = "Post Not Found", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })
	@PutMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(@PathVariable int postId) {
		return blogPostService.updateDraft(postId);
	}

	
	@Operation(description = "this endpoit  is used to update a Post Based On postId", responses = {
			@ApiResponse(responseCode = "200", description = "Post is Updated "),
			@ApiResponse(responseCode = "400", description = "invalid inputs", content = @Content(schema = @Schema(implementation = ErrorStructure.class))),
			@ApiResponse(responseCode = "404", description = "Post Not Found", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })
	@PutMapping("/blog-posts/{postId}/post")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updatePost(@Valid @RequestBody BlogPostRequestDTO blogPostRequestDTO,
			@PathVariable int postId) {
		return blogPostService.updatePost(blogPostRequestDTO,postId);
	}
	
	@Operation(description = "this endpoit  is used to update a Post Based On postId", responses = {
			@ApiResponse(responseCode = "200", description = "Post is Updated "),
			@ApiResponse(responseCode = "400", description = "invalid inputs", content = @Content(schema = @Schema(implementation = ErrorStructure.class))),
			@ApiResponse(responseCode = "404", description = "Post Not Found", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })
	@DeleteMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<String>> deleteBlogPost(@PathVariable int postId){
		return blogPostService.deleteBlogPost(postId);
	}

	@Operation(description = "this endpoit  is used to Fetch the Blog  Post Based On postId", responses = {
			@ApiResponse(responseCode = "200", description = "Post is Fetched "),
			@ApiResponse(responseCode = "400", description = "invalid inputs", content = @Content(schema = @Schema(implementation = ErrorStructure.class))),
			@ApiResponse(responseCode = "404", description = "Post Not Found", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })
	@GetMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> fetchBlogPost(@PathVariable int postId){
		return blogPostService.fetchBlogPost(postId);
	}
	
	@Operation(description = "this endpoit  is used to Find the PostType", responses = {
			@ApiResponse(responseCode = "200", description = "Post is Fetched "),
			@ApiResponse(responseCode = "400", description = "invalid inputs", content = @Content(schema = @Schema(implementation = ErrorStructure.class))),
			@ApiResponse(responseCode = "404", description = "Post Not Found", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })
	@GetMapping("/blog-posts/{postId}/Post")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findByIdAndPostType(@PathVariable int postId){
		return blogPostService.findByIdAndPostType(postId);
	}
}
