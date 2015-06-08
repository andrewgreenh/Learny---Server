package de.learny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.learny.controller.exception.NotEnoughPermissionsException;
import de.learny.controller.exception.ResourceNotFoundException;
import de.learny.controller.exception.ResourceWithSameNameException;
import de.learny.dataaccess.AccountRepository;
import de.learny.dataaccess.SubjectRepository;
import de.learny.domain.Account;
import de.learny.domain.Achievement;
import de.learny.domain.Role;
import de.learny.domain.Subject;
import de.learny.security.service.LoggedInAccountService;
import de.learny.security.service.PasswordGeneratorService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	PasswordGeneratorService passwordGenerator;

	@Autowired
	private LoggedInAccountService userToAccountService;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private SubjectRepository subjectRepo;

	@RequestMapping(value = "", method = RequestMethod.GET)
	Iterable<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	void create(@RequestBody Account account) {
		Account newAcc = new Account(account.getAccountName(),
		        passwordGenerator.hashPassword(account.getPassword()));
		try {
			accountRepository.save(newAcc);
		} catch (Exception e) {
			throw new ResourceWithSameNameException();
		}
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
		// TODO: Noch keine Funktionalität implementiert
		return accountRepository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	Account update(@PathVariable("id") long id, @RequestBody Account postedAccount) {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		if (!loggedInAccount.getAccountName().equals("admin") && loggedInAccount.getId() != id) {
			throw new NotEnoughPermissionsException();
		}
		Account oldAccount = accountRepository.findById(id);
		if (oldAccount == null) {
			throw new ResourceNotFoundException();
		}
		oldAccount.setSurname(postedAccount.getSurname());
		oldAccount.setLastname(postedAccount.getLastname());
		oldAccount.setEmail(postedAccount.getEmail());
		oldAccount.setAvatarUri(postedAccount.getAvatarUri());
		return accountRepository.save(oldAccount);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") long id) {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		if (!loggedInAccount.getAccountName().equals("admin")) {
			throw new NotEnoughPermissionsException();
		}
		accountRepository.delete(id);
	}

	@RequestMapping(value = "/me/enroled-subjects", method = RequestMethod.GET)
	Iterable<Subject> getEnroledSubjects() {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		return loggedInAccount.getJoinedSubjects();
	}

	@RequestMapping(value = "/me/enroled-subjects", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	boolean registerToSubjects(@RequestBody Subject subject) {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		Subject subjectToReg = subjectRepo.findById(subject.getId());
		if (subjectToReg == null)
			throw new ResourceNotFoundException();
		boolean var = loggedInAccount.addJoinedSubject(subjectToReg);
		accountRepository.save(loggedInAccount);
		return var;
	}

	@RequestMapping(value = "/me/administrated-subjects", method = RequestMethod.GET)
	Iterable<Subject> getAdministratedSubjects() {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		return loggedInAccount.getAdministratedSubjects();
	}

	@RequestMapping(value = "/me/achievements", method = RequestMethod.GET)
	Iterable<Achievement> getOwnAchievments() {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		return loggedInAccount.getAchievements();
	}

	@RequestMapping(value = "/me/statistics", method = RequestMethod.GET)
	void getOwnStatistics() {
		// TODO: Noch keine Funktionalität implementiert
		// return null;
	}
}
