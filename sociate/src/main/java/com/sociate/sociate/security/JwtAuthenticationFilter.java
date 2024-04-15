package com.sociate.sociate.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService customUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String TOKEN = getToken(request);
			if (TOKEN != null) {
				String userName = JwtUtil.extractUserName(TOKEN);
				UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
//				System.out.println(userDetails);

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, "",
						userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				System.err.println(authToken);

			}

		} catch (Exception e) {
			System.out.print(e);
		}

		filterChain.doFilter(request, response);

	}

	private String getToken(HttpServletRequest req) {
		String header = req.getHeader("Authorization");
		if (header == null || !header.contains("Bearer ")) {
			return null;
		}
		return header.substring(7, header.length());
	}

}
