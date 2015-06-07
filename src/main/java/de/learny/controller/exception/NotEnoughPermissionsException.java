package de.learny.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="You don't have enough permissions to do this.")
public class NotEnoughPermissionsException extends RuntimeException{

}
