package com.example.cms.utility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cms.Enums.PostType;
import com.example.cms.entity.BlogPost;
import com.example.cms.repositary.BlogPostRepository;

@Component
public class ScheduledJobs {

	private BlogPostRepository blogPostRepository;	
	
	
public ScheduledJobs(BlogPostRepository blogPostRepository) {
		super();
		this.blogPostRepository = blogPostRepository;
	}


//	@Scheduled(fixedDelay = 3000)
//	public void logDatTime() {
//		System.out.println((LocalDateTime.now()));
//	}
	
	@Scheduled(fixedDelay = 60*1000l)
	public void publishScheduleBlogPost() {
		List<BlogPost> posts = blogPostRepository.findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime.now(),PostType.SCHEDULE)
				.stream().map(post ->{
					post.setPostType(PostType.PUBLISHED);
					return post;
				}).collect(Collectors.toList());
		blogPostRepository.saveAll(posts);
	}
}
