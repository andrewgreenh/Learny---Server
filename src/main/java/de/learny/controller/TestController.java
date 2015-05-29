package de.learny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.learny.dataaccess.TestRepository;
import de.learny.domain.Test;

@RestController
@RequestMapping("/api/tests")
public class TestController {
	
	@Autowired
	private TestRepository testRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	Iterable<Test> getAllTests(){
		return testRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Test getTest(@PathVariable("id") long id){
		Test test = null;
		try {  
			   test = testRepository.findById(id);  
			  
			  } catch (Exception e) {  
			   e.printStackTrace();  
			  }  
		return test;
	}
}
