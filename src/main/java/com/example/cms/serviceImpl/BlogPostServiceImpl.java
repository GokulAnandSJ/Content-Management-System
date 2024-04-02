package com.example.cms.serviceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.DAO.BlogPostRequestDTO;
import com.example.cms.DAO.BlogPostResponse;
import com.example.cms.Enums.PostType;
import com.example.cms.entity.Blog;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.User;
import com.example.cms.exception.BlogIdNotFoundException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.repositary.BlogPostRepository;
import com.example.cms.repositary.BlogRepository;
import com.example.cms.repositary.ContributionPanelRepository;
import com.example.cms.repositary.UserRepository;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

@Service
public class BlogPostServiceImpl implements BlogPostService {

	private BlogRepository blogRepository;
	private BlogPostRepository blogPostRepository;
	private ResponseStructure<BlogPostResponse> responseStrecture;
	private BlogPostResponse blogPostResponse;
	private UserRepository userRepository;
	private ContributionPanelRepository contributionPanelRepository;


public BlogPostServiceImpl(BlogRepository blogRepository, BlogPostRepository blogPostRepository,
			ResponseStructure<BlogPostResponse> responseStrecture, BlogPostResponse blogPostResponse,
			UserRepository userRepository, ContributionPanelRepository contributionPanelRepository) {
		super();
		this.blogRepository = blogRepository;
		this.blogPostRepository = blogPostRepository;
		this.responseStrecture = responseStrecture;
		this.blogPostResponse = blogPostResponse;
		this.userRepository = userRepository;
		this.contributionPanelRepository = contributionPanelRepository;
	}


//	String email = SecurityContextHolder.getContext().getAuthentication().getName();
	public BlogPost mapToBlogPostEntity(BlogPostRequestDTO blogPostRequestDTO) {
		BlogPost blogPost = new BlogPost();
		blogPost.setTitle(blogPostRequestDTO.getTitle());
		blogPost.setSubTitle(blogPostRequestDTO.getSubTitle());
		blogPost.setSummary(blogPostRequestDTO.getSummary());
		blogPost.setPostType(PostType.DRAFT);
		return blogPost;
	}


	public BlogPostResponse mapToResponse(BlogPost blogPost) {
		return blogPostResponse.setTitle(blogPost.getTitle()).setPostId(blogPost.getPostId())
				.setSummary(blogPost.getSummary()).setPostType(blogPost.getPostType())
				.setCreatedAt(blogPost.getCreatedAt()).setCreatedBy(blogPost.getCreatedBy())
				.setSubTitle(blogPost.getSubTitle())
				.setLastModifiedAt(blogPost.getLastModifiedAt()).setLastModifiedBy(blogPost.getLastModifiedBy());
	}


	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(BlogPostRequestDTO blogPostRequest,
			int blogId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogRepository.findById(blogId).map(blog -> {

			if (validation(email, blog)) {
				BlogPost blogPost = mapToBlogPostEntity(blogPostRequest);
				blogPost.setBlog(blog);
				blogPost = blogPostRepository.save(blogPost);
				return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value())
					   .setMessage("Blog Post Created Sucessfully").setData(mapToResponse(blogPost)));
			}
			throw new IllegalAccessRequestException("IA");
		}).orElseThrow(() -> new BlogIdNotFoundException("Entered Blog Id is Not Found"));
	}


	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updatePost(BlogPostRequestDTO blogPostRequest,int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(post ->{
			if(validation(email, post.getBlog())) {
			post.setTitle(blogPostRequest.getTitle());
			post.setSubTitle(blogPostRequest.getSubTitle());
			post.setSummary(blogPostRequest.getSummary());
			post.setBlog(post.getBlog());
			post = blogPostRepository.save(post);
			return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value()).setMessage("post is updated..")
					.setData(mapToResponse(post)));
			}
			throw new IllegalAccessRequestException("Illegial Access");
		}).orElseThrow(() -> new BlogIdNotFoundException("blogPost not found by Given id"));
	}
	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(post -> {
			if(validation(email, post.getBlog())) {
			post.setPostType(PostType.PUBLISHED);
			post = blogPostRepository.save(post);
			return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value())
					.setMessage("post is updated Draft To Published").setData(mapToResponse(post)));
			}
			throw new IllegalAccessRequestException("Illegial Access");
		}).orElseThrow(() -> new BlogIdNotFoundException("Entered Blog Id is Not Found please Check"));
	}

	private boolean validation(String email,Blog blog) {
		return userRepository.findByEmail(email).map(user -> {
			if (blog.getUser().getEmail().equals(email)
					|| contributionPanelRepository.existsByPanelIdAndUsers(blog.getContributionPanel().getPanelId(), user))
				return true;
			return false;
		}).get();
	}


//	@Override
//	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId) {
//		String email = SecurityContextHolder.getContext().getAuthentication().getName();
//		 return blogPostRepository.findById(postId).map(post ->{
//			 if(validation(email, post.getBlog())) {
//				 post = blogPostRepository.deleteById(postId);
//			 }
//		});
//	}

}
