package com.sociate.sociate.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sociate.sociate.models.User;
import com.sociate.sociate.repository.UserRepository;
import com.sociate.sociate.security.UserPrincipal;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("Invalid username.");
		}
		
		
		return new UserPrincipal(user.getId(),user.getUsername(),user.getFirst_name(),user.getLast_name(),user.getEmail(),user.getPassword(), new ArrayList<>());
	}

}
