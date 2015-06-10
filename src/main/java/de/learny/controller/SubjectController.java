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
import de.learny.dataaccess.AccountRepository;
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
	
	@Autowired
	private AccountRepository accountRep;

	@RequestMapping(method = RequestMethod.GET)
	Iterable<Subject> getAllSubject() {
		return subjectRep.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Subject getSubject(@PathVariable("id") long id) {
		Subject subject = subjectRep.findById(id);
		if (subject == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		return subject;
	}

	@RequestMapping(value = "/{id}/tests", method = RequestMethod.GET)
	Iterable<Test> getAllTestsForSubject(@PathVariable("id") long id) {
		Subject subject = subjectRep.findById(id);
		if (subject == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		return subject.getTests();
	}
	
	@RequestMapping(value = "/{id}/tests", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	Boolean addTestForSubject(@PathVariable("id") long id, @RequestBody Test test) {
		Subject subject = subjectRep.findById(id);
		if (subject == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
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
			throw new NotEnoughPermissionsException("Nicht genug Rechte, um das auszuführen.");
		}
		this.subjectRep.save(subject);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") long id){
		//Übeprüft ob Subject vorhaden
		Subject subject = subjectRep.findById(id);
		if (subject == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		
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
			throw new NotEnoughPermissionsException("Nicht genug Rechte, um das auszuführen.");
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
	Subject update(@PathVariable("id") long id, @RequestBody Subject postedSubject){
		// Übeprüft ob Subject vorhaden
		Subject oldSubjcet = subjectRep.findById(id);
		if (oldSubjcet == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
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
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		return subject.getAccountsInCharge();
	}
	
	@RequestMapping(value = "/{id}/responsibles", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	boolean addResponsible(@PathVariable("id") long id, @RequestBody Account account) {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		if (!loggedInAccount.getAccountName().equals("admin")) {
			throw new NotEnoughPermissionsException("Nicht genug Rechte, um das auszuführen.");
		}
		
		Subject subject = subjectRep.findById(id);
		if (subject == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		boolean var = subject.addAccountInCharge(loggedInAccount);
		subjectRep.save(subject);
		return var;
	}
	
	@RequestMapping(value = "{subjectId}/responsibles/{userId}", method = RequestMethod.DELETE)
	boolean removeResponsible(@PathVariable("subjectId") long subjectId, @PathVariable("userId") long userId){
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		if (!loggedInAccount.getAccountName().equals("admin")) {
			throw new NotEnoughPermissionsException("Nicht genug Rechte, um das auszuführen.");
		}
		
		Account toRemoveAccount = accountRep.findById(userId);
		if (toRemoveAccount == null)
			throw new ResourceNotFoundException("Ein Account mit dieser id existiert nicht");
		
		Subject subject = subjectRep.findById(subjectId);
		if (subject == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		
		//FIXME: Löschen wird nicht vorgenohmen
		boolean var = subject.removeAccountInCharge(toRemoveAccount);
		subjectRep.save(subject);
		return var;
	}
}
