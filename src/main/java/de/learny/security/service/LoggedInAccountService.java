package de.learny.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import de.learny.dataaccess.AccountRepository;
import de.learny.domain.Account;

@Service
public class LoggedInAccountService {

	@Autowired
	AccountRepository accountRepository;
	
	public Account getLoggedInAccount(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = accountRepository.findFirstByAccountName(user.getUsername());
		return account;
	}
	
}
