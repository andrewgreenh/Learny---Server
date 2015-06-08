package de.learny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.learny.controller.exception.NotEnoughPermissionsException;
import de.learny.controller.exception.ResourceNotFoundException;
import de.learny.dataaccess.SubjectRepository;
import de.learny.domain.Account;
import de.learny.domain.Subject;
import de.learny.domain.Test;
import de.learny.security.service.LoggedInAccountService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

	@Autowired
	private SubjectRepository subjectRep;
	
	@Autowired
	private LoggedInAccountService userToAccountService;

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
		if (subject == null)
			throw new ResourceNotFoundException();
		return subject.getTests();
	}
	
	@RequestMapping(value = "/{id}/tests", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	Boolean addTestForSubject(@PathVariable("id") long id, @RequestBody Test test) {
		Subject subject = subjectRep.findById(id);
		if (subject == null)
			throw new ResourceNotFoundException();
		//TODO: Soll hier auch ein neuer Test erstellt werden?
		boolean var = false;
		if(permitted(id)){
			var = subject.addTest(test);
			subjectRep.save(subject);
		}
		return var;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	void create(@RequestBody Subject subject){
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		if (!loggedInAccount.getAccountName().equals("admin")) {
			throw new NotEnoughPermissionsException();
		}
		this.subjectRep.save(subject);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") long id){
		//Übeprüft ob Subject vorhaden
		Subject subject = subjectRep.findById(id);
		if (subject == null)
			throw new ResourceNotFoundException();
		
		if (permitted(id)) {
			this.subjectRep.delete(id);
		}
	}
	
	private boolean permitted(long id){
		// Übeprüft ob Subject vorhaden
		Subject subject = subjectRep.findById(id);
		// Überprüft ob Account Admin oder verantwortlich für Subject
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		boolean inCharge = false;
		inCharge = subject.getAccountsInCharge().contains(loggedInAccount);
		if (inCharge || loggedInAccount.getAccountName().equals("admin")) {
			return true;
		} else {
			throw new NotEnoughPermissionsException();
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
	Subject update(@PathVariable("id") long id, @RequestBody Subject postedSubject){
		// Übeprüft ob Subject vorhaden
		Subject oldSubjcet = subjectRep.findById(id);
		if (oldSubjcet == null)
			throw new ResourceNotFoundException();
		if (permitted(id)) {
			oldSubjcet.setName(postedSubject.getName());
			oldSubjcet.setDescription(oldSubjcet.getDescription());
		}
		return this.subjectRep.save(oldSubjcet);
	}
	
	@RequestMapping(value = "/{id}/responsibles", method = RequestMethod.GET)
	Iterable<Account> getResponsibles(@PathVariable("id") long id) {
		Subject subject = subjectRep.findById(id);
		if (subject == null)
			throw new ResourceNotFoundException();
		return subject.getAccountsInCharge();
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
