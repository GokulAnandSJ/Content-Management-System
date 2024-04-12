package com.example.cms.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.DAO.BlogPostResponse;
import com.example.cms.DAO.PublishRequestDTO;
import com.example.cms.DAO.PublishResponse;
import com.example.cms.DAO.ScheduleRequestDTO;
import com.example.cms.Enums.PostType;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.entity.Schedule;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.InvalidDateTimeexception;
import com.example.cms.exception.PostIdNotFoundException;
import com.example.cms.repositary.BlogPostRepository;
import com.example.cms.repositary.PublishRepository;
import com.example.cms.repositary.ScheduleRepository;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

@Service
public class PublishServiceImpl implements PublishService {

	private BlogPostRepository blogPostRepository;
	private ResponseStructure<BlogPostResponse> blogPostStrecture;
	private ResponseStructure<PublishResponse> publishStrecture;;
	private PublishRepository publishRepository;
	private ScheduleRepository scheduleRepository;

	public PublishServiceImpl(BlogPostRepository blogPostRepository,
			ResponseStructure<BlogPostResponse> blogPostStrecture, ResponseStructure<PublishResponse> publishStrecture,
			PublishRepository publishRepository, ScheduleRepository scheduleRepository) {
		super();
		this.blogPostRepository = blogPostRepository;
		this.blogPostStrecture = blogPostStrecture;
		this.publishStrecture = publishStrecture;
		this.publishRepository = publishRepository;
		this.scheduleRepository = scheduleRepository;
	}

	private Publish mapToPublishEntity(PublishRequestDTO publishRequestDTO, Publish publish) {

		publish.setSeoTitle(publishRequestDTO.getSeoTitle());
		publish.setSeoDescription(publishRequestDTO.getSeoDescription());
		publish.setSeoTags(publishRequestDTO.getSeoTags());

		return publish;
	}

	private PublishResponse mapToResponse(Publish publish) {
		PublishResponse publishResponse = new PublishResponse();
		if (publish.getSchedule() != null) {
			publishResponse.setSeoTitle(publish.getSeoTitle());
			publishResponse.setSeoDescription(publish.getSeoDescription());
			publishResponse.setSeoTags(publish.getSeoTags()).setPublishId(publish.getPublishId());
			publishResponse.setSchedule(publish.getSchedule());
			publishResponse.setPublishId(publish.getPublishId());
			return publishResponse;
		} else {
			publishResponse.setSeoTitle(publish.getSeoTitle());
			publishResponse.setSeoDescription(publish.getSeoDescription());
			publishResponse.setSeoTags(publish.getSeoTags()).setPublishId(publish.getPublishId());
			publishResponse.setSchedule(null);
			return publishResponse;
		}

	}

	private BlogPostResponse mappToBlogResponse(BlogPost post) {
		BlogPostResponse blogpostResponse = new BlogPostResponse();
		blogpostResponse.setTitle(post.getTitle()).setSubTitle(post.getSubTitle()).setSummary(post.getSummary())
				.setPostId(post.getPostId()).setCreatedBy(post.getCreatedBy());

		return blogpostResponse;
	}

	private Publish mapToPublish(PublishRequestDTO publishRequestDTO) {
		Publish publish = new Publish();
		if (publishRequestDTO.getSchedule() != null) {
			publish.setSeoTitle(publishRequestDTO.getSeoTitle());
			publish.setSeoDescription(publishRequestDTO.getSeoDescription());
			publish.setSeoTags(publishRequestDTO.getSeoTags());
			publish.setSchedule(maptoSchedule(publishRequestDTO.getSchedule()));
			return publish;
		} else {
			publish.setSeoTitle(publishRequestDTO.getSeoTitle());
			publish.setSeoDescription(publishRequestDTO.getSeoDescription());
			publish.setSeoTags(publishRequestDTO.getSeoTags());
			publish.setSchedule(null);
			return publish;
		}

	}

	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequestDTO publishRequestDTO,
			int blogPostId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return blogPostRepository.findById(blogPostId).map(post -> {
			Publish publish = null;
			if (post.getBlog().getUser().getEmail().equals(email) || post.getCreatedBy().equals(email)) {

				if (post.getPublish() != null) {
					publish = mapToPublishEntity(publishRequestDTO, post.getPublish());
				} else {
					publish = mapToPublish(publishRequestDTO);
				}
				if (publishRequestDTO.getSchedule() != null) {
					Schedule schedule = publish.getSchedule();
					if (publishRequestDTO.getSchedule().getDateTime().isAfter(LocalDateTime.now())) {
						if (schedule == null) {
							publish.setBlogPost(post);
							publish.setSchedule(
									scheduleRepository.save(maptoSchedule(publishRequestDTO.getSchedule())));
							publish = publishRepository.save(publish);
							post.setPostType(PostType.SCHEDULE);
							post = blogPostRepository.save(post);
							return ResponseEntity.ok(publishStrecture.setStatusCode(HttpStatus.OK.value())
									.setMessage("Post is Published").setData(mapToResponse(publish)));
						} else {

							publish.setBlogPost(post);
							scheduleRepository.save(maptoSchedule(publishRequestDTO.getSchedule(), schedule));
							publish = publishRepository.save(publish);
							post.setPostType(PostType.SCHEDULE);
							post = blogPostRepository.save(post);
							return ResponseEntity.ok(publishStrecture.setStatusCode(HttpStatus.OK.value())
									.setMessage("Post is Published Sucessfully").setData(mapToResponse(publish)));
						}
					}
					throw new InvalidDateTimeexception("Entered Date Time is Invalid Please Enter Feture time");

				} else {
					publish.setBlogPost(post);
					post.setPostType(PostType.PUBLISHED);
					publishRepository.save(publish);
					post = blogPostRepository.save(post);
					return ResponseEntity.ok(publishStrecture.setStatusCode(HttpStatus.OK.value())
							.setMessage("Post is Published Sucessfully").setData(mapToResponse(publish)));
				}
			}
			throw new IllegalAccessRequestException("Ownner Can Delete the Post");
		}).orElseThrow(() -> new PostIdNotFoundException("Entered Post Id is Not Found Please Check"));
	}

	private Schedule maptoSchedule(ScheduleRequestDTO schedule, Schedule schedule2) {
		schedule2.setScheduleId(schedule2.getScheduleId());
		schedule2.setDateTime(schedule.getDateTime());
		return schedule2;
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unpublishedBlogPost(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return blogPostRepository.findById(postId).map(post -> {
			if (post.getBlog().getUser().getEmail().equals(email) || post.getCreatedBy().equals(email)) {
				post.setPostType(PostType.DRAFT);
				post = blogPostRepository.save(post);
				return ResponseEntity.ok(blogPostStrecture.setStatusCode(HttpStatus.OK.value())
						.setMessage("Post is UnPublished Sucessfully").setData(mappToBlogResponse(post)));
			}
			throw new IllegalAccessRequestException("Illegial Access Found");
		}).orElseThrow(() -> new PostIdNotFoundException("Entered Post Id is not Found Please Check"));
	}

	private Schedule maptoSchedule(ScheduleRequestDTO scheduleRequestDTO) {
		Schedule schedule = new Schedule();
		schedule.setDateTime(scheduleRequestDTO.getDateTime());
		return schedule;
	}
}

