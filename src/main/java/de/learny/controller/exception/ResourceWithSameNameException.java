package de.learny.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT,reason="Resource with name already existent")
public class ResourceWithSameNameException extends RuntimeException{

}
