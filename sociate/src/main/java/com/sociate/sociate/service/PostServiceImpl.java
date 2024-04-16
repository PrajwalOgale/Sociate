package com.sociate.sociate.service;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sociate.sociate.dto.AddPostDto;
import com.sociate.sociate.dto.GetPostsDto;
import com.sociate.sociate.models.Post;
import com.sociate.sociate.models.User;
import com.sociate.sociate.repository.PostsRepository;
import com.sociate.sociate.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PostsRepository postRepo;

	@Value("${file.upload.locations}")
	private String[] picPath;

	@Override
	public String addPost(AddPostDto post, String username) {

		try {
			Post newPost = new Post();
			if (!post.getImage().isEmpty()) {
				String postPath = picPath[2] + "/" + username + post.getImage().getOriginalFilename();
				File postImage = new File(postPath);
				writeByteArrayToFile(postImage, post.getImage().getBytes());
				newPost.setImage(postPath);
			}
			User user = userRepo.findByUsername(username);
			newPost.setUser(user);
			System.out.println(LocalDate.now());
			newPost.setCreationTime(LocalDate.now());
			newPost.setDescription(post.getDescription());
			newPost.setLikes(0L);
			postRepo.save(newPost);
			return "Post added successfully.";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "Post didn't add.";
	}

	@Override
	public List<GetPostsDto> getAllPostByUserOrFollowingUsers(Long userId) {
		try {

			List<GetPostsDto> postsDto = new ArrayList<>();
			System.out.println(userId);
			List<Post> posts = postRepo.getAllPostsByUserAndFollowingUsers(userId)
					.orElseThrow(() -> new RuntimeException("User is Invalid."));
			posts.stream().forEach(p -> {
				try {

					GetPostsDto post = new GetPostsDto();
					String postPicPath = p.getImage();
					File postPic = new File(postPicPath);

					post.setPostId(p.getId());
					post.setCreationTime(p.getCreationTime());
					post.setDescription(p.getDescription());
					post.setLikes(p.getLikes());
					post.setUsername(p.getUser().getUsername());
					post.setImage(readFileToByteArray(postPic));
					postsDto.add(post);
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			});
			return postsDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public String deletePostByUser(Long postId,Long userId) {
		Post post = postRepo.findById(postId).orElseThrow(()-> new RuntimeException("Invalid post id."));
		if(post.getUser().getId()!=userId) {
			return "Post can not be deleted.";
		}
		post.setUser(null);
		postRepo.delete(post);
		return "post deleted successfully.";
	}

	@Override
	public Long likePostById(Long postId, Long id) {
		
		return null;
	}

}
