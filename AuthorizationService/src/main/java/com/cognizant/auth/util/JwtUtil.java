package com.cognizant.auth.util;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utility class for JWT
 * 
 * @author Sunmeet
 *
 */
@Component
public class JwtUtil {

	@Value("${app.secretKey}")
	private String secretKey;

	@Value("${app.jwtValidityMinutes}")
	private long jwtValidityMinutes;

	/**
	 * Generates a JWT token using the given subject
	 * 
	 * @param subject username of the user
	 * @return JWT
	 */
	public String generateToken(String subject) {
		return Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis())).setSubject(subject)
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(jwtValidityMinutes)))
				.signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(secretKey.getBytes())).compact();
	}

	/**
	 * Validate a token by checking expiry and validating username
	 * 
	 * @param token    JWT
	 * @param username
	 * @return Boolean representing whether token is valid
	 */
	public boolean validateToken(String token, String username) {
		return !isTokenExpired(token) && username.equals(getUsernameFromToken(token));
	}

	/**
	 * Gets username from the token
	 * 
	 * @param token JWT
	 * @return username from the token
	 */
	public String getUsernameFromToken(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * Gets Claims
	 * 
	 * @param token JWT
	 * @return claims from the token
	 */
	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(Base64.getEncoder().encode(secretKey.getBytes())).parseClaimsJws(token)
				.getBody();
	}

	/**
	 * Tells whether token is expired or not
	 * 
	 * @param token JWT
	 * @return Boolean representing whether token is expired
	 */
	public boolean isTokenExpired(String token) {
		try {
			getClaims(token);
		} catch (ExpiredJwtException e) {
			return true;
		}
		return false;
	}
}
