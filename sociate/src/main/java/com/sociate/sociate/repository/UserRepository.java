package com.sociate.sociate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sociate.sociate.models.User;


public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

}
