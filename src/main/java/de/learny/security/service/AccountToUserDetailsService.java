package de.learny.security.service;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.learny.dataaccess.AccountRepository;
import de.learny.domain.Account;



@Service
public class AccountToUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findFirstByAccountName(username);
		if (account == null) {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		return new User(username, account.getPassword(), getGrantedAuthorities(username));
	}

	private Collection<? extends GrantedAuthority> getGrantedAuthorities(String username) {
		Collection<? extends GrantedAuthority> authorities;
		if (username.equals("John")) {
			authorities = asList(() -> "ROLE_ADMIN", () -> "ROLE_BASIC");
			} else {
			authorities = asList(() -> "ROLE_BASIC");
			}

		return authorities;
	}
	
}
