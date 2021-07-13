package com.cognizant.auth.util;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cognizant.auth.exception.InvalidUsernameException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for JWT
 * 
 * @author Sunmeet
 *
 */
@Component
@Slf4j
public class JwtUtil {
	
	@Value("${app.secretKey}")
	private String secretKey;

	@Value("${app.jwtValidityMinutes}")
	private long jwtValidityMinutes;

	@Value("${jwtUtil.expiredMessage}")
	private String EXPIRED_MESSAGE;

	@Value("${jwtUtil.malformedMessage}")
	private String MALFORMED_MESSAGE;

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
		return !isTokenExpiredOrInvalidFormat(token) && validUsername(username, token);
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
	 * Tells whether token is expired or not and whether the token is in valid
	 * format or not
	 * 
	 * @param token JWT
	 * @return Boolean representing whether token is expired or if the token is in
	 *         invalid format
	 */
	public boolean isTokenExpiredOrInvalidFormat(String token) {
		try {
			getClaims(token);
		} catch (ExpiredJwtException e) {
			log.info(EXPIRED_MESSAGE);
			return true;
		} catch (MalformedJwtException e) {
			log.info(MALFORMED_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param username
	 * @param token
	 * @return boolean whether user name is valid or not
	 */
	public boolean validUsername(String username, String token) {
		if(username.equals(getUsernameFromToken(token))){
			return true;
		}
		throw new InvalidUsernameException("Incorrect Username");
	}
}
