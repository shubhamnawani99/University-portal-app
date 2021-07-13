package com.cognizant.student.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Class for customizing error response in exception handler
 * 
 * @author Sunmeet
 *
 */
@Data
public class ErrorResponse {

	private String message;
	private LocalDateTime timestamp;
	
	/**
	 * Used only for input validation errors
	 */
	@JsonInclude(Include.NON_NULL)
	private List<String> fieldErrors;
}
