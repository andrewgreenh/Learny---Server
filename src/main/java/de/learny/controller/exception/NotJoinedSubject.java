package de.learny.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class NotJoinedSubject extends RuntimeException{
	
	public NotJoinedSubject(String message) {
		super(message);
	}
}
