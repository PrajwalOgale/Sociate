package com.sociate.sociate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sociate.sociate.dto.ForgotPasswordDTO;
import com.sociate.sociate.dto.GetUserDTO;
import com.sociate.sociate.dto.LoginUserDTO;
import com.sociate.sociate.dto.RegisterUserDTO;
import com.sociate.sociate.dto.UpdateUserDTO;
import com.sociate.sociate.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public GetUserDTO getUser(@RequestParam(value = "username") String userName) {
		try {
			System.out.println(userName);
			GetUserDTO userDto = userService.getUserByUsername(userName);

			return userDto;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@ModelAttribute RegisterUserDTO user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginUserDTO user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.authenticate(user));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
		}
	}

	@GetMapping("/check_email_availability")
	public ResponseEntity<?> checkEmailAvailability(@RequestParam(value = "email") String email) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.checkEmail(email));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/check_username_availability")
	public ResponseEntity<?> checkUserNameAvailability(@RequestParam(value = "username") String username) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.checkUserName(username));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping("/forgot_password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.changePassword(user));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping
	public ResponseEntity<?> updateUser(@ModelAttribute UpdateUserDTO user) {
		try {
			System.out.println("contr");
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(user));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deletePassword(@RequestParam(value = "username") String username) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.deleteUser(username));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
