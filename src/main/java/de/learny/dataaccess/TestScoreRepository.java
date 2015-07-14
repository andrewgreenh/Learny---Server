package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Account;
import de.learny.domain.Test;
import de.learny.domain.TestScore;

public interface TestScoreRepository extends CrudRepository<TestScore, Long> {
	
	TestScore findFirstByAccount(Account account);
	
	Iterable<TestScore> findAll();
	
	TestScore findById(long id);
	
	TestScore findFirstByAccountAndTestOrderByTimestampDesc(Account account, Test test);
	
	Iterable<TestScore> findTop10ByTestOrderByScoreDesc(Test test);
	
	TestScore findByAccountAndTest(Account account, Test test);

}
