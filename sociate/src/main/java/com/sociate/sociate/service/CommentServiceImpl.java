package com.sociate.sociate.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sociate.sociate.dto.AddCommentDto;
import com.sociate.sociate.dto.GetAllCommentsDto;
import com.sociate.sociate.models.Comment;
import com.sociate.sociate.models.Post;
import com.sociate.sociate.models.User;
import com.sociate.sociate.repository.CommentsRepository;
import com.sociate.sociate.repository.PostsRepository;
import com.sociate.sociate.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentsRepository commentRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PostsRepository postsRepo;

	@Override
	public String addComment(AddCommentDto comment, Long userId) {
		try {

			Post post = postsRepo.findById(comment.getPostId())
					.orElseThrow(() -> new RuntimeException("Post doesn't exists."));
			User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User doesn't exists."));

			Comment newComment = new Comment();
			newComment.setCreationTime(LocalDate.now());
			newComment.setDesciption(comment.getDescription());
			newComment.setPost(post);
			newComment.setUser(user);
			newComment.setLikes(0L);

			commentRepo.save(newComment);

			return "Comment added.";
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<GetAllCommentsDto> getAllCommentsByPostId(Long postId) {
		List<Comment> comments = commentRepo.findAllByPostId(postId);
		List<GetAllCommentsDto> commentsDto = new ArrayList<>();
		
		comments.stream().forEach((c) -> {
			GetAllCommentsDto comment = new GetAllCommentsDto();
			comment.setCreationTime(c.getCreationTime());
			comment.setDesciption(c.getDesciption());
			comment.setId(c.getId());
			comment.setLikes(c.getLikes());
			comment.setPostId(c.getPost().getId());
			comment.setUserId(c.getUser().getId());
			commentsDto.add(comment);
		});

		return commentsDto;
	}

	@Override
	public String deleteCommentById(Long commentId, Long userId) {
		Comment comment = commentRepo.findById(commentId).orElseThrow(()-> new RuntimeException("Comment doesn't exists."));
		if(comment.getUser().getId()!=userId) {
			return "This comment can be not delete by current user.";
		}
		comment.setUser(null);
		comment.setPost(null);
		commentRepo.delete(comment);
		return "comment got deleted.";
	}

}
