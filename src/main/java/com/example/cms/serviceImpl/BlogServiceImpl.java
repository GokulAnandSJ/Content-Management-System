package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.cms.DAO.BlogRequestDTO;
import com.example.cms.DAO.BlogResponse;
import com.example.cms.entity.Blog;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.exception.BlogIdNotFoundException;
import com.example.cms.exception.TitleNotAvailableException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserIdIsNotFound;
import com.example.cms.repositary.BlogRepository;
import com.example.cms.repositary.ContributionPanelRepository;
import com.example.cms.repositary.UserRepository;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

@Service
public class BlogServiceImpl implements BlogService {

	private BlogRepository blogRepository;
	private ResponseStructure<BlogResponse> responseStrecture;
	private UserRepository userRepository;
	private BlogResponse blogResponse;
	private ResponseStructure<Boolean> titleStrecture;
	private ContributionPanelRepository panelRepository;



	public BlogServiceImpl(BlogRepository blogRepository, ResponseStructure<BlogResponse> responseStrecture,
			UserRepository userRepository, BlogResponse blogResponse, ResponseStructure<Boolean> titleStrecture,
			ContributionPanelRepository panelRepository) {
		super();
		this.blogRepository = blogRepository;
		this.responseStrecture = responseStrecture;
		this.userRepository = userRepository;
		this.blogResponse = blogResponse;
		this.titleStrecture = titleStrecture;
		this.panelRepository = panelRepository;
	}
	private Blog mapToBlogEntity(BlogRequestDTO blogRequestDTO) {
		Blog blog = new Blog();

		blog.setTitle(blogRequestDTO.getTitle());
		blog.setTopics(blogRequestDTO.getTopics());
		blog.setAbout(blogRequestDTO.getAbout());

		return blog;
	}
	private BlogResponse mapToBlogResponse(Blog blog) {
		return blogResponse.setBlogId(blog.getBlogId())
				.setTopics(blog.getTopics())
				.setTitle(blog.getTitle())
				.setAbout(blog.getAbout());
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(BlogRequestDTO blogRequestDTO , int userId) {

		return	userRepository.findById(userId).map(user->{
			if(blogRepository.existsByTitle(blogRequestDTO.getTitle()))
				throw new TitleNotAvailableException("Failed to Create Tiltle Because this Title already Exists");
			if(blogRequestDTO.getTopics().length <1) {
				throw new TopicNotSpecifiedException("Failed to create Blog Please write content");
			}
			
			Blog blog = mapToBlogEntity(blogRequestDTO);
			ContributionPanel contributionPanel =panelRepository.save(new ContributionPanel());
			blog.setContributionPanel(contributionPanel);
			blog.setUser(user);
			blog = blogRepository.save(blog);
			
			userRepository.save(user);
			return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value()).setMessage("Blog Created Sucessfully")
					.setData(mapToBlogResponse(blog)));
		}).orElseThrow(()-> new UserIdIsNotFound("Entered User Id Is Not Found Please Give proper Id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<Boolean>> checkForBlogTitleAvailability(String title) {
		if(blogRepository.existsByTitle(title)) {
			return ResponseEntity.ok(titleStrecture.setStatusCode(HttpStatus.OK.value()).setMessage("Title Is Already Present").setData(true));
		}

		return ResponseEntity.ok(titleStrecture.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value()).setMessage("Entered Title is Not Takes You can Use").setData(false));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId) {
		return blogRepository.findById(blogId).map(blog ->{
			return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value())
					.setMessage("Blog Id Found Sucessfully ").setData(mapToBlogResponse(blog)));
		}).orElseThrow(()-> new BlogIdNotFoundException("Blog Id Is not Found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> ubdateBlogData(int blogId, BlogRequestDTO updatedBlog) {
		return blogRepository.findById(blogId).map(exBlog->{
			Blog blog = mapToBlogEntity(updatedBlog);
			blog.setUser(exBlog.getUser());
			blog.setBlogId(exBlog.getBlogId());
			exBlog=blogRepository.save(blog);
			return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value())
					.setMessage("Blog Updated Sucessfully")
					.setData(mapToBlogResponse(blog)));
		}).orElseThrow(() -> new BlogIdNotFoundException("Blog Id Not Found Please Check"));
	}
}
