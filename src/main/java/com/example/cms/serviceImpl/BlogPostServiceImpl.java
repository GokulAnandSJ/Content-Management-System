package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.cms.DAO.BlogPostRequestDTO;
import com.example.cms.DAO.BlogPostResponse;
import com.example.cms.DAO.PublishResponse;
import com.example.cms.Enums.PostType;
import com.example.cms.entity.Blog;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.exception.BlogIdNotFoundException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PostIdNotFoundException;
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
	private UserRepository userRepository;
	private ContributionPanelRepository contributionPanelRepository;
	private ResponseStructure<String> stringStrecture;

	public BlogPostServiceImpl(BlogRepository blogRepository, BlogPostRepository blogPostRepository,
			ResponseStructure<BlogPostResponse> responseStrecture, UserRepository userRepository,
			ContributionPanelRepository contributionPanelRepository, ResponseStructure<String> stringStrecture) {
		super();
		this.blogRepository = blogRepository;
		this.blogPostRepository = blogPostRepository;
		this.responseStrecture = responseStrecture;
		this.userRepository = userRepository;
		this.contributionPanelRepository = contributionPanelRepository;
		this.stringStrecture = stringStrecture;
	}

	public BlogPost mapToBlogPostEntity(BlogPostRequestDTO blogPostRequestDTO , String email) {
		BlogPost blogPost = new BlogPost();
		blogPost.setTitle(blogPostRequestDTO.getTitle());
		blogPost.setSubTitle(blogPostRequestDTO.getSubTitle());
		blogPost.setSummary(blogPostRequestDTO.getSummary());
		blogPost.setPostType(PostType.DRAFT);
		blogPost.setCreatedBy(email);
		blogPost.setLastModifiedBy(email);
		return blogPost;
	}

	public BlogPostResponse mapToResponse(BlogPost blogPost) {
		
		BlogPostResponse blogPostResponses = new BlogPostResponse();
		blogPostResponses.setCreatedBy(blogPost.getCreatedBy());
		   blogPostResponses.setTitle(blogPost.getTitle());
		   blogPostResponses.setPostId(blogPost.getPostId());
		   blogPostResponses.setSummary(blogPost.getSummary());
		   blogPostResponses.setSubTitle(blogPost.getSubTitle());
		   blogPostResponses.setPublishResponse(mappToPublishRespons(blogPost.getPublish()));

		return blogPostResponses;
	}

	private PublishResponse mappToPublishRespons(Publish publish) {

		if(publish==null)
		{
			return null;
		}
		PublishResponse publishResponse = new PublishResponse()
		.setPublishId(publish.getPublishId())
		.setSeoDescription(publish.getSeoDescription())
		.setSeoTags(publish.getSeoTags())
		.setSeoTitle(publish.getSeoTitle());
	
	return publishResponse;
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(BlogPostRequestDTO blogPostRequest,
			int blogId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogRepository.findById(blogId).map(blog -> {

			if (validation(email, blog)) {
				BlogPost blogPost = mapToBlogPostEntity(blogPostRequest,email);
				blogPost.setBlog(blog);
				blogPost = blogPostRepository.save(blogPost);
				return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value())
						.setMessage("Blog Post Created Sucessfully").setData(mapToResponse(blogPost)));
			}
			throw new IllegalAccessRequestException("IA");
		}).orElseThrow(() -> new BlogIdNotFoundException("Entered Blog Id is Not Found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updatePost(BlogPostRequestDTO blogPostRequest,
			int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(post -> {
			if (validation(email, post.getBlog())) {
				post.setTitle(blogPostRequest.getTitle());
				post.setSubTitle(blogPostRequest.getSubTitle());
				post.setSummary(blogPostRequest.getSummary());
				post.setBlog(post.getBlog());
				post.setLastModifiedBy(email);
				post = blogPostRepository.save(post);
				return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value())
						.setMessage("post is updated..").setData(mapToResponse(post)));
			}
			throw new IllegalAccessRequestException("Illegial Access");
		}).orElseThrow(() -> new BlogIdNotFoundException("blogPost not found by Given id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(post -> {
			if (validation(email, post.getBlog())) {
				post.setPostType(PostType.PUBLISHED);
				post = blogPostRepository.save(post);
				return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value())
						.setMessage("post is updated Draft To Published").setData(mapToResponse(post)));
			}
			throw new IllegalAccessRequestException("Illegial Access");
		}).orElseThrow(() -> new BlogIdNotFoundException("Entered Blog Id is Not Found please Check"));
	}

	private boolean validation(String email, Blog blog) {
		return userRepository.findByEmail(email).map(user -> {
			if (blog.getUser().getEmail().equals(email) || contributionPanelRepository
					.existsByPanelIdAndUsers(blog.getContributionPanel().getPanelId(), user))
				return true;
			return false;
		}).get();
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteBlogPost(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(post -> {
			if (post.getBlog().getUser().getEmail().equals(email) || post.getCreatedBy().equals(email)) {
				blogPostRepository.delete(post);
				return ResponseEntity.ok(stringStrecture.setStatusCode(HttpStatus.OK.value())
						.setMessage("Blog Post Deleted Secessfully").setData("Post Deleted"));
			}
			throw new IllegalAccessRequestException("Illegial Access");
		}).orElseThrow(() -> new PostIdNotFoundException("Entered Post Id is Not Found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> fetchBlogPost(int blogPostId) {

		return blogPostRepository.findById(blogPostId).map(post -> {
			responseStrecture.setStatusCode(HttpStatus.FOUND.value()).setMessage("Blog Post Found Sucessfully")
					.setData(mapToResponse(post));
			return ResponseEntity.ok(responseStrecture);
		}).orElseThrow(() -> new PostIdNotFoundException("Entered Post Id is Not found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findByIdAndPostType(int postId) {

		return blogPostRepository.findByPostIdAndPostType(postId, PostType.PUBLISHED).map(post ->{

		return ResponseEntity.status(HttpStatus.FOUND)
				.body(responseStrecture.setStatusCode(HttpStatus.OK.value()).setMessage("Published Found Sucessfully")
						.setData(mapToResponse(post)));
		}).orElseThrow(() -> new PostIdNotFoundException("Entered Blog is Not Found Or Not in publisher"));
	}

}
