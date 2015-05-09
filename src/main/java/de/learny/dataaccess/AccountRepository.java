package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

	Account findFirstByAccountName(String accountName);
	
	Iterable<Account> findAll();
	
	Account findById(long id);
}
