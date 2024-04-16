package com.sociate.sociate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sociate.sociate.dto.AddPostDto;
import com.sociate.sociate.security.UserPrincipal;
import com.sociate.sociate.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping
	public ResponseEntity<?> addPost(@ModelAttribute AddPostDto post, Authentication auth) {
		try {

			String username = auth.getName();
			System.out.println("Username ");
			System.out.println(username);
			return ResponseEntity.status(HttpStatus.CREATED).body(postService.addPost(post, username));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}

	}

	@GetMapping
	public ResponseEntity<?> getAllPostsByUserAndFollowingUsers(Authentication auth) {
		try {

			UserPrincipal user = (UserPrincipal) auth.getPrincipal();

			System.out.println("user.getId()  ");
			System.out.println(user.getId());
			return ResponseEntity.status(HttpStatus.OK)
					.body(postService.getAllPostByUserOrFollowingUsers(user.getId()));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}

	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Long postId, Authentication auth) {
		try {

			UserPrincipal user = (UserPrincipal) auth.getPrincipal();

			System.out.println("user.getId()  ");
			System.out.println(user.getId());
			return ResponseEntity.status(HttpStatus.OK).body(postService.deletePostByUser(postId, user.getId()));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}

	}

	@PutMapping("/{postId}")
	public ResponseEntity<?> likePost(@PathVariable Long postId, Authentication auth) {
		try {

			UserPrincipal user = (UserPrincipal) auth.getPrincipal();

			return ResponseEntity.status(HttpStatus.OK).body(postService.likePostById(postId, user.getId()));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}

	}
}
