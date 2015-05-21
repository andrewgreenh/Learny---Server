package de.learny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.learny.dataaccess.AccountRepository;
import de.learny.domain.Account;
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

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	Account getOwnAccounts() {
		Account account = userToAccountService.getLoggedInAccount();
		return account;
	}

	@RequestMapping(value = "/loggedin", method = RequestMethod.GET)
	boolean checkLogin() {
		return true;
	}

}
