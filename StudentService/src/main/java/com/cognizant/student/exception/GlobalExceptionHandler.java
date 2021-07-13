package com.cognizant.student.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author Shubham Nawani
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles input validation errors
	 * @author Sunmeet
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorResponse response = new ErrorResponse();
		response.setMessage("Invalid Credentails");
		response.setTimestamp(LocalDateTime.now());

		// Get all validation errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> {
			return x.getField() + ": " + x.getDefaultMessage();
		}).collect(Collectors.toList());

		// Add errors to the response map
		response.setFieldErrors(errors);

		return new ResponseEntity<>(response, headers, status);
	}

	/**
	 * Handles invalid semester exception
	 * 
	 * @param invalidSemesterException
	 * @return Response Entity of type error Response
	 */
	@ExceptionHandler(InvalidSemesterException.class)
	public ResponseEntity<ErrorResponse> handlesSemesterInvalidException(
			InvalidSemesterException invalidSemesterException) {

		ErrorResponse response = new ErrorResponse();
		response.setMessage(invalidSemesterException.getMessage());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handles the proper length of user-name
	 * 
	 * @param invalidUsernameException
	 * @return Response Entity of type error Response
	 */
	@ExceptionHandler(InvalidUsernameException.class)
	public ResponseEntity<ErrorResponse> handlesUserInvalidException(
			InvalidUsernameException invalidUsernameException) {

		ErrorResponse response = new ErrorResponse();
		response.setMessage(invalidUsernameException.getMessage());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}