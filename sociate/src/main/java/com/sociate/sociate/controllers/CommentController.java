package com.sociate.sociate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sociate.sociate.dto.AddCommentDto;
import com.sociate.sociate.security.UserPrincipal;
import com.sociate.sociate.service.CommentService;

@RequestMapping("/api/comments")
@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping
	public ResponseEntity<?> addComment(@RequestBody AddCommentDto comment, Authentication auth) {
		try {

			UserPrincipal user = (UserPrincipal) auth.getPrincipal();

			return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(comment, user.getId()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}

	}

	@GetMapping("/{postId}")
	public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
		try {

			return ResponseEntity.status(HttpStatus.CREATED).body(commentService.getAllCommentsByPostId(postId));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}

	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteCommentsById(@PathVariable Long commentId, Authentication auth) {
		try {
			UserPrincipal user = (UserPrincipal) auth.getPrincipal();

			return ResponseEntity.status(HttpStatus.CREATED).body(commentService.deleteCommentById(commentId, user.getId()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}

	}

}
