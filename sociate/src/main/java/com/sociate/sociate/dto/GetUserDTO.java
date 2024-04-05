package com.sociate.sociate.dto;

import jakarta.persistence.Column;
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
public class GetUserDTO {

	private String username;

	private String email;

	private String first_name;

	private String last_name;

	private byte[] coverPic;

	private byte[] profilePic;
	private String city;
	private String website;

}
