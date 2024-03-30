package com.sociate.sociate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sociate.sociate.dto.RegisterUserDTO;
import com.sociate.sociate.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String getUsers() {
		return "all users";
	}
	
	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO user){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}
	}
	
	@GetMapping("/check_email_availability")
	public ResponseEntity<?> checkEmailAvailability(@RequestParam(value = "email") String email){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.checkEmail(email));
		}catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
