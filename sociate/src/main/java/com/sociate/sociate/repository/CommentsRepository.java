package com.sociate.sociate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sociate.sociate.models.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long>{

}
