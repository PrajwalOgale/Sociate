package com.sociate.sociate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sociate.sociate.models.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long>{

	List<Comment> findAllByPostId(Long postId);
}
