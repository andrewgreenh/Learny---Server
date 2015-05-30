package de.learny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.learny.controller.exception.ResourceNotFoundException;
import de.learny.dataaccess.SubjectRepository;
import de.learny.dataaccess.TestRepository;
import de.learny.domain.Subject;
import de.learny.domain.Test;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
	
	@Autowired
	private SubjectRepository subjectRep;
	
	@Autowired
	private TestRepository testRep;

	@RequestMapping(value = "", method = RequestMethod.GET)
	Iterable<Subject> getAllSubject(){
		return subjectRep.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Subject getSubject(@PathVariable("id") long id){
		Subject subject = subjectRep.findById(id);	  
		if(subject == null)
			throw new ResourceNotFoundException();
		return subject;
	}
	
	@RequestMapping(value = "/{id}/tests", method = RequestMethod.GET)
	Iterable<Test> getAllTestsForSubject(@PathVariable("id") long id){
		Subject subject = subjectRep.findById(id);
		return testRep.findBySubject(subject);
	}
}
