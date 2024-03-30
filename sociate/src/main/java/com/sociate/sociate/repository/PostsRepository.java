package com.sociate.sociate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sociate.sociate.models.Post;

public interface PostsRepository extends JpaRepository<Post, Long>{

}
