package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Account;
import de.learny.domain.TestScore;

public interface TestScoreRepository extends CrudRepository<TestScore, Long> {
	
	TestScore findFirstByAccount(Account account);
	
	Iterable<TestScore> findAll();
	
	TestScore findById(long id);

}