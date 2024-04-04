package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.cms.DAO.BlogPostResponse;
import com.example.cms.DAO.PublishRequestDTO;
import com.example.cms.DAO.PublishResponse;
import com.example.cms.Enums.PostType;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PostIdNotFoundException;
import com.example.cms.repositary.BlogPostRepository;
import com.example.cms.repositary.PublishRepository;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;
@Service
public class PublishServiceImpl implements PublishService {

	private BlogPostRepository blogPostRepository;
	private ResponseStructure<BlogPostResponse> blogPostStrecture;
	private ResponseStructure<PublishResponse> publishStrecture;;
	private PublishRepository publishRepository;


	public PublishServiceImpl(BlogPostRepository blogPostRepository,
			ResponseStructure<BlogPostResponse> blogPostStrecture, ResponseStructure<PublishResponse> publishStrecture,
			PublishRepository publishRepository) {
		super();
		this.blogPostRepository = blogPostRepository;
		this.blogPostStrecture = blogPostStrecture;
		this.publishStrecture = publishStrecture;
		this.publishRepository = publishRepository;
	}

	private Publish mapToPublishEntity(PublishRequestDTO publishRequestDTO) {
		Publish publish = new Publish();
		
		publish.setSeoTitle(publishRequestDTO.getSeoTitle());
		publish.setSeoDescription(publishRequestDTO.getSeoDescription());
		publish.setSeoTags(publishRequestDTO.getSeoTags());

		return publish;
	}

	private PublishResponse mapToResponse(Publish publish ) {
		PublishResponse publishResponse = new PublishResponse();
		
		 publishResponse.setSeoTitle(publish.getSeoTitle());
		 publishResponse.setSeoDescription(publish.getSeoDescription());
		 publishResponse.setSeoTags(publish.getSeoTags()).setPublishId(publish.getPublishId());
		 return publishResponse; 
	}

	private BlogPostResponse mappToBlogResponse(BlogPost post) {
		BlogPostResponse blogpostResponse = new BlogPostResponse();
		blogpostResponse.setTitle(post.getTitle()).setSubTitle(post.getSubTitle()).setSummary(post.getSummary())
		.setCreatedBy(post.getCreatedBy());

		return blogpostResponse;
	}

	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequestDTO publishRequestDTO , int blogPostId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return blogPostRepository.findById(blogPostId).map(post ->{
			if(post.getBlog().getUser().getEmail().equals(email) || post.getCreatedBy().equals(email)) {
				Publish publish = mapToPublishEntity(publishRequestDTO);
				publish.setBlogPost(post);
				publish = publishRepository.save(publish);
				post.setPostType(PostType.PUBLISHED);
				post = blogPostRepository.save(post);
				return ResponseEntity.ok(publishStrecture.setStatusCode(HttpStatus.OK.value())
						.setMessage("Post is Published Sucessfully").setData(mapToResponse(publish)));
			}
			throw new IllegalAccessRequestException("Illegial Access");
		}).orElseThrow(() -> new PostIdNotFoundException("Entered Post Id is Not Found Please Check"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unpublishedBlogPost(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return blogPostRepository.findById(postId).map(post ->{
			if(post.getBlog().getUser().getEmail().equals(email)|| post.getCreatedBy().equals(email)) {
				post.setPostType(PostType.DRAFT);
				post = blogPostRepository.save(post);
				return ResponseEntity.ok(blogPostStrecture.setStatusCode(HttpStatus.OK.value()).setMessage("Post is UnPublished Sucessfully")
						.setData(mappToBlogResponse(post)));
			}
			throw new IllegalAccessRequestException("Illegial Access Found");
		}).orElseThrow(()-> new PostIdNotFoundException("Entered Post Id is not Found Please Check"));
	}



}
