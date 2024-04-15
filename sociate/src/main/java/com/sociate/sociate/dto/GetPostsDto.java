package com.sociate.sociate.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.sociate.sociate.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostsDto {

	private LocalDate creationTime;
	private String description;
	private byte[] image;
	private String username;
	private Long likes;

}
