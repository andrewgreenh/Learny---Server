package de.learny.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.learny.controller.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

	@ExceptionHandler
	void handle(IllegalArgumentException e, HttpServletResponse response)
	        throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}
	
	@ExceptionHandler
	void handle(ResourceNotFoundException e, HttpServletResponse response)
	        throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

}
