package com.sociate.sociate.dto;

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
public class UpdateUserDTO {

	private String username;
	
	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private String coverPic;

	private String profilePic;
	private String city;
	private String website;
}
