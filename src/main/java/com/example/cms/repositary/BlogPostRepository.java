package com.example.cms.repositary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.Enums.PostType;
import com.example.cms.entity.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {

	Optional<BlogPost> findByPostIdAndPostType(int postId, PostType published);

	List<BlogPost> findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime now, PostType schedule);

}
