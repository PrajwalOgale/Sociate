package com.sociate.sociate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sociate.sociate.models.User;


public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

	User findByUsername(String userName);

}
