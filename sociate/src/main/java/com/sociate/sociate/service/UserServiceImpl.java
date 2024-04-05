package com.sociate.sociate.service;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.stereotype.Service;

import com.sociate.sociate.dto.ForgotPasswordDTO;
import com.sociate.sociate.dto.GetUserDTO;
import com.sociate.sociate.dto.RegisterUserDTO;
import com.sociate.sociate.dto.UpdateUserDTO;
import com.sociate.sociate.dto.UserEmailAvailabilityDto;
import com.sociate.sociate.dto.UserNameAvailabilityDto;
import com.sociate.sociate.models.User;
import com.sociate.sociate.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Value("${file.upload.locations}")
	private String[] picPath;

	@Override
	public String addUser(RegisterUserDTO userDto) {

		try {
//			System.out.println(picPath[0]);
			String profilePicPath = picPath[0] + "/profile-image.png";
			String coverPicPath = picPath[0] + "/cover-image.png";
			if (!userDto.getProfilePic().isEmpty()) {
				System.out.println(userDto.getProfilePic());
				profilePicPath = picPath[0] + "/" + userDto.getUsername()
						+ userDto.getProfilePic().getOriginalFilename();
				File profilePic = new File(profilePicPath);
				writeByteArrayToFile(profilePic, userDto.getProfilePic().getBytes());
			}
			if (!userDto.getCoverPic().isEmpty()) {

				coverPicPath = picPath[1] + "/" + userDto.getUsername() + userDto.getCoverPic().getOriginalFilename();
				File coverPic = new File(coverPicPath);
				writeByteArrayToFile(coverPic, userDto.getCoverPic().getBytes());
			}

			User user = new User(null, userDto.getUsername(), userDto.getEmail(), userDto.getPassword(),
					userDto.getFirstName(), userDto.getLastName(), coverPicPath, profilePicPath, userDto.getCity(),
					userDto.getWebsite());

			userRepo.save(user);
			return "User created";
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public UserEmailAvailabilityDto checkEmail(String email) {
		if (!checkEmailValidity(email)) {
			throw new RuntimeException("Invalid Email.");
		}
		User user = userRepo.findByEmail(email);
		if (user == null)
			return new UserEmailAvailabilityDto(true);
		return new UserEmailAvailabilityDto(false);
	}

	private boolean checkEmailValidity(String email) {
		String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	@Override
	public GetUserDTO getUserByUsername(String userName) {

		User user = userRepo.findByUsername(userName);
		if (user == null)
			throw new RuntimeException("Invalid Username.");
		try {

			File profilePic = new File(user.getProfilePic());
			File coverPic = new File(user.getCoverPic());

			GetUserDTO userDto = new GetUserDTO(user.getUsername(), user.getEmail(), user.getFirst_name(),
					user.getLast_name(), readFileToByteArray(coverPic), readFileToByteArray(profilePic), user.getCity(),
					user.getWebsite());

			return userDto;
		} catch (Exception e) {
			throw new RuntimeException("Images does not exist.");
		}
	}

	@Override
	public UserNameAvailabilityDto checkUserName(String username) {
		User user = userRepo.findByUsername(username);
		if (user != null)
			return new UserNameAvailabilityDto(false);

		return new UserNameAvailabilityDto(true);
	}

	@Override
	public String changePassword(ForgotPasswordDTO user) {
		User persistentUser = userRepo.findByUsername(user.getUsername());
		if (persistentUser == null)
			throw new RuntimeException("Invalid username.");
		persistentUser.setPassword(user.getPassword());
		userRepo.save(persistentUser);
		return "Password changed.";
	}

	@Override
	public String deleteUser(String username) {
		User persistentUser = userRepo.findByUsername(username);
		if (persistentUser == null)
			throw new RuntimeException("User does not exists.");

		userRepo.delete(persistentUser);
		return "user deleted.";
	}

	@Override
	public String updateUser(UpdateUserDTO user) {
		User persistentUser = userRepo.findByUsername(user.getUsername());
		if (persistentUser == null)
			throw new RuntimeException("Invalid User.");

		persistentUser.setEmail(user.getEmail());
		persistentUser.setPassword(user.getPassword());
		persistentUser.setCity(user.getCity());
		persistentUser.setWebsite(user.getWebsite());
		persistentUser.setFirst_name(user.getFirstName());
		persistentUser.setLast_name(user.getLastName());
		persistentUser.setProfilePic(user.getProfilePic());
		persistentUser.setCoverPic(user.getCoverPic());

		userRepo.save(persistentUser);
		return "User details Updated";
	}

}
