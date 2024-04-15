package com.sociate.sociate.models;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 1000)
	private String desciption;
	@Column(nullable = false)
	private LocalDate creationTime;
	private Long likes;
	//userid is id of user who commented
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private User user;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Post post;
}
