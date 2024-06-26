package com.sociate.sociate.dto;

import org.springframework.web.multipart.MultipartFile;

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
public class RegisterUserDTO {

	private String username;

	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private MultipartFile coverPic;
	private MultipartFile profilePic;
	
	private String city;
	private String website;
}
