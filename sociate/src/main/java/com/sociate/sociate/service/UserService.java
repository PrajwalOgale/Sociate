package com.sociate.sociate.service;

import com.sociate.sociate.dto.ForgotPasswordDTO;
import com.sociate.sociate.dto.GetUserDTO;
import com.sociate.sociate.dto.LoginUserDTO;
import com.sociate.sociate.dto.RegisterUserDTO;
import com.sociate.sociate.dto.UpdateUserDTO;
import com.sociate.sociate.dto.UserEmailAvailabilityDto;
import com.sociate.sociate.dto.UserNameAvailabilityDto;

public interface UserService {

	String addUser(RegisterUserDTO userDto);

	UserEmailAvailabilityDto checkEmail(String email);

	GetUserDTO getUserByUsername(String userName);

	UserNameAvailabilityDto checkUserName(String username);

	String changePassword(ForgotPasswordDTO user);

	String deleteUser(String username);

	String updateUser(UpdateUserDTO user);

	String authenticate(LoginUserDTO user);
}
