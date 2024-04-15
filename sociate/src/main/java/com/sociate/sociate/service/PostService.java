package com.sociate.sociate.service;

import java.util.List;

import com.sociate.sociate.dto.AddPostDto;
import com.sociate.sociate.dto.GetPostsDto;

public interface PostService {

	String addPost(AddPostDto post, String username);
	
	List<GetPostsDto> getAllPostByUserOrFollowingUsers(Long userId);
}
