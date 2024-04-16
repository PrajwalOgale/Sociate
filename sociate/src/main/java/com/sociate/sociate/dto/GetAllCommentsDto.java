package com.sociate.sociate.dto;

import java.time.LocalDate;

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
public class GetAllCommentsDto {

	private Long id;

	private String desciption;

	private LocalDate creationTime;
	private Long likes;

	private Long userId;
	private Long postId;

}
