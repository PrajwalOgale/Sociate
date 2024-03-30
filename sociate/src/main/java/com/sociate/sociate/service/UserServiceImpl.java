package com.sociate.sociate.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sociate.sociate.dto.RegisterUserDTO;
import com.sociate.sociate.dto.UserEmailAvailabilityDto;
import com.sociate.sociate.models.User;
import com.sociate.sociate.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public String addUser(RegisterUserDTO userDto) {

		try {

			User user = new User(null, "string", userDto.getEmail(), userDto.getPassword(), userDto.getFirstName(),
					userDto.getLastName(), null, null, userDto.getCity(), userDto.getWebsite());

			userRepo.save(user);
			return "User created";
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public UserEmailAvailabilityDto checkEmail(String email){
		if(!checkEmailValidity(email)) {
			throw new RuntimeException("Invalid Email.");
		}
		User user = userRepo.findByEmail(email);
		if (user == null)
			return new UserEmailAvailabilityDto(true);
		return new UserEmailAvailabilityDto(false);
	}

	private boolean checkEmailValidity(String email) {
		String EMAIL_PATTERN =
	            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

}
