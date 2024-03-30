package com.sociate.sociate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, length = 500, nullable = false)
	private String username;
	@Column(unique = true, length = 500, nullable = false)
	private String email;
	@Column(length = 1000, nullable = false)
	private String password;
	@Column(length = 500, nullable = false)
	private String first_name;
	@Column(length = 500, nullable = false)
	private String last_name;
	@Column(length = 500)
	private String coverPic;
	@Column(length = 500)
	private String profilePic;
	private String city;
	private String website;

}
