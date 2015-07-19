package de.learny.dataaccess;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Account;
import de.learny.domain.PasswordResetToken;

public interface AccountRepository extends CrudRepository<Account, Long> {

	Account findFirstByAccountName(String accountName);
	
	Iterable<Account> findAll();
	
	Account findById(long id);
	
	Iterable<Account> findByRoles(Set roles);
	
	Account findByEmail(String email);
	
	Account findByPasswordResetToken(PasswordResetToken token);
}
