package de.learny.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class InvalidPasswordException extends RuntimeException{
	
	public InvalidPasswordException(String message) {
		super(message);
	}
	
}
