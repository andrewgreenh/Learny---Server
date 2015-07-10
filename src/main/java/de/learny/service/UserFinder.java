package de.learny.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.learny.dataaccess.AccountRepository;
import de.learny.domain.Account;

@Service
public class UserFinder {
	
	@Autowired
	AccountRepository accountRepo;
	
	Iterable<Account> accounts;
	
	public Set<Account> findUserBy(String string) {
		accounts = accountRepo.findAll();
		Set<Account> resultSet = new HashSet<Account>();
		for(Account account: accounts) {
			if(accountMatchesString(account, string)) {
				resultSet.add(account);
			}
			if(resultSet.size() > 10) {
				return resultSet;
			}
		}
		return resultSet;
	}
	
	public Set<Account> findUserBy(String string, String role) {
		accounts = accountRepo.findAll();
		Set<Account> resultSet = new HashSet<Account>();
		for(Account account: accounts) {
			if(account.hasRole(role)) {
				if(accountMatchesString(account, string)) {
					resultSet.add(account);
				}
				if(resultSet.size() > 10) {
					return resultSet;
				}
			}
		}
		return resultSet;
	}
	
	private boolean accountMatchesString(Account account, String string) {
		return account.getAccountName().toLowerCase().contains(string.toLowerCase());
	}
}
