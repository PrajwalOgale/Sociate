package com.sociate.sociate.service;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sociate.sociate.dto.ForgotPasswordDTO;
import com.sociate.sociate.dto.GetUserDTO;
import com.sociate.sociate.dto.LoginUserDTO;
import com.sociate.sociate.dto.RegisterUserDTO;
import com.sociate.sociate.dto.UpdateUserDTO;
import com.sociate.sociate.dto.UserEmailAvailabilityDto;
import com.sociate.sociate.dto.UserNameAvailabilityDto;
import com.sociate.sociate.models.User;
import com.sociate.sociate.repository.UserRepository;
import com.sociate.sociate.security.JwtUtil;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private PasswordEncoder passEncoder;

	@Value("${file.upload.locations}")
	private String[] picPath;

	@Override
	public String addUser(RegisterUserDTO userDto) {

		try {
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

			User user = new User(null, userDto.getUsername(), userDto.getEmail(),
					passEncoder.encode(userDto.getPassword()), userDto.getFirstName(), userDto.getLastName(),
					coverPicPath, profilePicPath, userDto.getCity(), userDto.getWebsite());

			userRepo.save(user);
			String token = JwtUtil.generateToken(userDto.getUsername());

			return token;
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
		persistentUser.setPassword(passEncoder.encode(user.getPassword()));
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
		try {

			User persistentUser = userRepo.findByUsername(user.getUsername());
			if (persistentUser == null)
				throw new RuntimeException("Invalid User.");

			String profilePicPath = picPath[0] + "/profile-image.png";
			String coverPicPath = picPath[0] + "/cover-image.png";
			if (!user.getProfilePic().isEmpty()) {
				if (persistentUser.getProfilePic() != profilePicPath) {
					File oldProfilePic = new File(persistentUser.getProfilePic());
					oldProfilePic.delete();
				}
				System.out.println(user.getProfilePic());
				profilePicPath = picPath[0] + "/" + user.getUsername() + user.getProfilePic().getOriginalFilename();
				File profilePic = new File(profilePicPath);
				writeByteArrayToFile(profilePic, user.getProfilePic().getBytes());
			}
			if (!user.getCoverPic().isEmpty()) {
				if (persistentUser.getProfilePic() != coverPicPath) {
					File oldCoverPic = new File(persistentUser.getCoverPic());
					oldCoverPic.delete();
				}
				coverPicPath = picPath[1] + "/" + user.getUsername() + user.getCoverPic().getOriginalFilename();
				File coverPic = new File(coverPicPath);
				writeByteArrayToFile(coverPic, user.getCoverPic().getBytes());
			}

			persistentUser.setEmail(user.getEmail());
			persistentUser.setPassword(passEncoder.encode(user.getPassword()));
			persistentUser.setCity(user.getCity());
			persistentUser.setWebsite(user.getWebsite());
			persistentUser.setFirst_name(user.getFirstName());
			persistentUser.setLast_name(user.getLastName());
			persistentUser.setProfilePic(profilePicPath);
			persistentUser.setCoverPic(coverPicPath);

			userRepo.save(persistentUser);
			return "User details Updated";
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return "User does not updated";
	}

	@Override
	public String authenticate(LoginUserDTO user) {
		try {

			authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

			User u = userRepo.findByUsername(user.getUsername());
			if (u == null) {
				throw new RuntimeException("User does not exists.");
			}
			return JwtUtil.generateToken(user.getUsername());

		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException(e.getMessage());
		}
	}

}
