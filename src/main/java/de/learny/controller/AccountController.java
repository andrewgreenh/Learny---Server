package de.learny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.learny.dataaccess.AccountRepository;
import de.learny.domain.Account;
import de.learny.domain.Achievement;
import de.learny.domain.Role;
import de.learny.domain.Subject;
import de.learny.security.service.LoggedInAccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private LoggedInAccountService userToAccountService;

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	Iterable<Account> getAllAccounts() {
		return accountRepository.findAll();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	void create(@RequestBody Account account) {
		//TODO: Noch keine Funktionalität implementiert
	}

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	Account getOwnAccounts() {
		Account account = userToAccountService.getLoggedInAccount();
		return account;
	}

	@RequestMapping(value = "/loggedin", method = RequestMethod.GET)
	boolean checkLogin() {
		return true;
	}
	
	@RequestMapping(value = "/{role}", method = RequestMethod.GET)
	Iterable<Account> getAllAccountsToRole(@PathVariable("role") Role role) {
		//TODO: Noch keine Funktionalität implementiert
		return accountRepository.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
	Account update(@PathVariable("id") long id, @RequestBody Account updateAccount){
		//TODO: Funktioniert nur wenn in der JSON-Datei die ID steht. Außerdem ist es kein update mehr sondern ein überschreiben
		//Subject subject = this.subjectRep.findById(id);
		//BeanUtils.copyProperties(subject, updateSubject);
		return this.accountRepository.save(updateAccount);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") long id){
		//TODO: Noch keine richtige Funktionalität implementiert
		this.accountRepository.delete(id);
	}
	
	@RequestMapping(value = "/me/enroled-subjects", method = RequestMethod.GET)
	Iterable<Subject> getEnroledSubjects() {
		//TODO: Noch keine richtige Funktionalität implementiert
		return null;
	}
	
	@RequestMapping(value = "/me/enroled-subjects", method = RequestMethod.POST)
	boolean registerToSubjects(@RequestBody Subject subject) {
		//TODO: Noch keine Funktionalität implementiert
		return true;
	}
	
	@RequestMapping(value = "/me/administrated-subjects", method = RequestMethod.GET)
	Iterable<Subject> getAdministratedSubjects() {
		//TODO: Noch keine Funktionalität implementiert
		return null;
	}
	
	@RequestMapping(value = "/me/achievements", method = RequestMethod.GET)
	Iterable<Achievement> getOwnAchievments() {
		//TODO: Noch keine Funktionalität implementiert
		return null;
	}
	
	@RequestMapping(value = "/me/statistics", method = RequestMethod.GET)
	void getOwnStatistics() {
		//TODO: Noch keine Funktionalität implementiert
		//return null;
	}
}
