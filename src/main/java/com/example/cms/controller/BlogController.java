package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.DAO.BlogRequestDTO;
import com.example.cms.DAO.BlogResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
public class BlogController {

	private BlogService blogService;

	public BlogController(BlogService blogService) {
		super();
		this.blogService = blogService;
	}
	
	@Operation(description = "this endpoint is used to Create the Blog" , responses = {
			@ApiResponse(responseCode = "200", description = "Blog Created Sucessfully"),
			@ApiResponse(responseCode = "400" , description = "Invalid Input" , content = @Content(schema = @Schema(implementation = ErrorStructure.class ))),
			@ApiResponse(responseCode = "404" , description = "Invalid Input" , content = @Content(schema = @Schema(implementation = ErrorStructure.class )))})
	@PostMapping("/users/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(@RequestBody @Valid BlogRequestDTO blogRequestDTO , @PathVariable int userId ){
		return blogService.createBlog(blogRequestDTO, userId);
		
	}
	
	@Operation(description = "this endpoit  is used to check Title is Available or not ", responses = {
			@ApiResponse(responseCode = "200", description = "Title is Available You can Creat "),
			@ApiResponse(responseCode = "404", description = "Title is Already Taken", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })
	@GetMapping("/titles/{title}/blogs")
	public  ResponseEntity<ResponseStructure<Boolean>> checkForBlogTitleAvailability( @PathVariable String title) {
		return blogService.checkForBlogTitleAvailability(title);
	}
	
	@Operation(description = "this endpoit  is used to check Blog is Present or not Based On BlogId ", responses = {
			@ApiResponse(responseCode = "200", description = "Blog is Found Sucessfully "),
			@ApiResponse(responseCode = "404", description = "Blog Id Not Found", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })
	@GetMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(@PathVariable int blogId){
		return blogService.findBlogById(blogId);
	}
	
	@Operation(description = "this endpoit  is used to Update the Blog Based On BlogId", responses = {
			@ApiResponse(responseCode = "200", description = "Blog is Updated Sucessfully "),
			@ApiResponse(responseCode = "400", description = "invalid inputs", content = @Content(schema = @Schema(implementation = ErrorStructure.class))),
			@ApiResponse(responseCode = "404", description = "Blog Not Found", content = @Content(schema = @Schema(implementation = ErrorStructure.class))) })
	@PutMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(@PathVariable int blogId , @RequestBody BlogRequestDTO updatedData){
		return blogService.ubdateBlogData(blogId, updatedData);
	}
}
