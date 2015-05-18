package de.learny.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class MainController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
    String hello() {
       return "Hello World";
    }
	
}
