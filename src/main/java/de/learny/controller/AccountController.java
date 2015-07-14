package de.learny.controller;

import io.swagger.annotations.Api;

import java.util.HashSet;
import java.util.Set;

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
import de.learny.dataaccess.AccountRepository;
import de.learny.dataaccess.RoleRepository;
import de.learny.dataaccess.SubjectRepository;
import de.learny.domain.Account;
import de.learny.domain.Achievement;
import de.learny.domain.Role;
import de.learny.domain.Subject;
import de.learny.security.service.LoggedInAccountService;
import de.learny.security.service.PasswordGeneratorService;
import de.learny.service.UserFinder;

@Api(value = "Accounts", description = "Zugriff auf Accounts", produces = "application/json")
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

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserFinder userFinder;

	@RequestMapping(value = "", method = RequestMethod.GET)
	Iterable<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	void create(@RequestBody Account account) {
		System.out.print(account.toString());
		if (account.getPassword() == null) {
			throw new IllegalArgumentException("Passwort darf nicht leer sein.");
		}
		Account newAcc = new Account(account.getAccountName(),
		        passwordGenerator.hashPassword(account.getPassword()));
		if (newAcc.getAccountName() == null) {
			throw new IllegalArgumentException("Accountname darf nicht leer sein");
		}

		newAcc.setFirstname(account.getFirstname());
		newAcc.setLastname(account.getLastname());
		newAcc.setEmail(account.getEmail());
		newAcc.addRole(roleRepo.findFirstByName("user"));

		boolean accountNameAlreadyExists = accountRepository.findFirstByAccountName(newAcc
		        .getAccountName()) != null;
		if (accountNameAlreadyExists) {
			throw new IllegalArgumentException("Accountname schon vorhanden");
		}
		accountRepository.save(newAcc);

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
	Iterable<Account> getAllAccountsToRole(@PathVariable("role") String role) {
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleRepo.findFirstByName(role));
		return accountRepository.findByRoles(roles);
	}

	@RequestMapping(value = "/find/{string}", method = RequestMethod.GET)
	Set<Account> findAccountByString(@PathVariable("string") String string) {
		return userFinder.findUserBy(string);
	}

	@RequestMapping(value = "/findwithrole/{string}&{role}", method = RequestMethod.GET)
	Set<Account> findAccountWithRoleByString(@PathVariable("string") String string,
	        @PathVariable("role") String role) {
		return userFinder.findUserBy(string, role);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	Account update(@PathVariable("id") long id, @RequestBody Account postedAccount) {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		if (!loggedInAccount.hasRole("admin") && loggedInAccount.getId() != id) {
			throw new NotEnoughPermissionsException("Nicht genug Rechte, um das durchzuführen.");
		}
		Account oldAccount = accountRepository.findById(id);
		if (oldAccount == null) {
			throw new ResourceNotFoundException("Ein Account mit dieser id existiert nicht");
		}
		oldAccount.setFirstname(postedAccount.getFirstname());
		oldAccount.setLastname(postedAccount.getLastname());
		oldAccount.setEmail(postedAccount.getEmail());
		oldAccount.setAvatarUri(postedAccount.getAvatarUri());
		oldAccount.setMyNote(postedAccount.getMyNote());
		return accountRepository.save(oldAccount);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") long id) {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		if (!loggedInAccount.hasRole("admin")) {
			throw new NotEnoughPermissionsException("Nicht genug Rechte, um das auszuführen.");
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
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		boolean var = loggedInAccount.addJoinedSubject(subjectToReg);
		accountRepository.save(loggedInAccount);
		return var;
	}

	@RequestMapping(value = "/me/enroled-subjects/{subjectId}", method = RequestMethod.DELETE)
	boolean dischargeFromSubject(@PathVariable("subjectId") long subjectId) {
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		Subject subjectToRemove = subjectRepo.findById(subjectId);
		if (subjectToRemove == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		boolean var = loggedInAccount.removeJoinedSubject(subjectToRemove);
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
