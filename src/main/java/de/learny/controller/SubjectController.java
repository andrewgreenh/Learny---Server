package de.learny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.learny.controller.exception.ResourceNotFoundException;
import de.learny.dataaccess.SubjectRepository;
import de.learny.domain.Account;
import de.learny.domain.Subject;
import de.learny.domain.Test;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

	@Autowired
	private SubjectRepository subjectRep;

	@RequestMapping(method = RequestMethod.GET)
	Iterable<Subject> getAllSubject() {
		return subjectRep.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Subject getSubject(@PathVariable("id") long id) {
		Subject subject = subjectRep.findById(id);
		if (subject == null)
			throw new ResourceNotFoundException();
		return subject;
	}

	@RequestMapping(value = "/{id}/tests", method = RequestMethod.GET)
	Iterable<Test> getAllTestsForSubject(@PathVariable("id") long id) {
		Subject subject = subjectRep.findById(id);
		return subject.getTests();
	}
	
	@RequestMapping(value = "/{id}/tests", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	void addTestForSubject(@PathVariable("id") long id, @RequestBody Test test) {
		//TODO: Muss noch implementiert werden
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	void create(@RequestBody Subject subject){
		//TODO: Nur Admin darf ein Subject erstellen
		this.subjectRep.save(subject);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") long id){
		this.subjectRep.delete(id);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
	Subject update(@PathVariable("id") long id, @RequestBody Subject updateSubject){
		//TODO: Funktioniert nur wenn in der JSON-Datei die ID steht. Außerdem ist es kein update mehr sondern ein überschreiben
		//Subject subject = this.subjectRep.findById(id);
		//BeanUtils.copyProperties(subject, updateSubject);
		return this.subjectRep.save(updateSubject);
	}
	
	@RequestMapping(value = "/{id}/responsibles", method = RequestMethod.GET)
	void getResponsibles(@PathVariable("id") long id) {
		//TODO: Muss noch implementiert werden
	}
	
	@RequestMapping(value = "/{id}/responsibles", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	void addResponsible(@PathVariable("id") long id, @RequestBody Account account) {
		//TODO: Muss noch implementiert werden
	}
	
	@RequestMapping(value = "{subjectId}/responsibles/{userId}", method = RequestMethod.DELETE)
	void removeResponsible(@PathVariable("subjectId") long subjectId, @PathVariable("userId") long userId){
		//TODO: Muss noch implementiert werden
	}
}
