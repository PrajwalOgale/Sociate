package com.sociate.sociate.service;

import com.sociate.sociate.dto.RegisterUserDTO;
import com.sociate.sociate.dto.UserEmailAvailabilityDto;

public interface UserService {

	String addUser(RegisterUserDTO userDto);

	UserEmailAvailabilityDto checkEmail(String email);

}
