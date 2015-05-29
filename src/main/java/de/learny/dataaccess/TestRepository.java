package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Subject;
import de.learny.domain.Test;

public interface TestRepository extends CrudRepository<Test, Long> {
	
	Test findFirstByTestName(String testName);
	
	Iterable<Test> findAll();
	
	Test findById(long id);
	
	Iterable<Test> findBySubject(Subject subject);
}
