package com.sociate.sociate.service;

import java.util.List;

import com.sociate.sociate.dto.AddCommentDto;
import com.sociate.sociate.dto.GetAllCommentsDto;

public interface CommentService {
	
	String addComment(AddCommentDto comment, Long userId);

	List<GetAllCommentsDto> getAllCommentsByPostId(Long postId);

	String deleteCommentById(Long commentId, Long userId);
}
