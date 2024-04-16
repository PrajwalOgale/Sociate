package com.sociate.sociate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sociate.sociate.models.Post;

public interface PostsRepository extends JpaRepository<Post, Long> {

	@Query("SELECT DISTINCT p FROM Post p LEFT JOIN p.user u LEFT JOIN Relation r ON r.followingUser.id = u.id WHERE u.id = :userId OR r.followerUser.id = :userId order by p.creationTime desc")
	Optional<List<Post>> getAllPostsByUserAndFollowingUsers(@Param("userId") Long userId);
}
