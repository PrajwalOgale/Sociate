package com.sociate.sociate.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "dhfjkhsjdhfjkhsdjfhashdfkhskljcfklsdjfklsjfkldsjklfFKJSADKLF";
	private static final Long EXPIRATION_TIME = (long) 864_000_000; // 10 DAYS

	public static String generateToken(String userName) {
		return Jwts.builder().setSubject(userName).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public static String extractUserName(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}
}
