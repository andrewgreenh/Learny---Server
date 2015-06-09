package de.learny.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class NotEnoughPermissionsException extends RuntimeException{
	
	public NotEnoughPermissionsException(String message) {
		super(message);
	}
	
}
