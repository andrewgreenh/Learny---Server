package de.learny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.learny.dataaccess.SubjectRepository;
import de.learny.domain.Subject;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
	
	@Autowired
	private SubjectRepository subjectRep;

	@RequestMapping(value = "", method = RequestMethod.GET)
	Iterable<Subject> getAllSubject(){
		return subjectRep.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Subject getSubject(@PathVariable("id") long id){
		Subject subject = null;
		try {  
			subject = subjectRep.findById(id);  
			  
			  } catch (Exception e) {  
			   e.printStackTrace();  
			  }  
		return subject;
	}
}
