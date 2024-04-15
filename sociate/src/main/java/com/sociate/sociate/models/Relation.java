package com.sociate.sociate.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "relations")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Relation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//followers of user
	@ManyToOne(cascade = CascadeType.ALL)
	private User followerUser;
	//user is following	
	@ManyToOne(cascade = CascadeType.ALL)
	private User followingUser;
}
