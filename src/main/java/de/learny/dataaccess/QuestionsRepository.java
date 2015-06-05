package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Question;

public interface QuestionsRepository extends CrudRepository<Question, Long> {
	
	Iterable<Question> findAll();
	
	Question findById(long id);

}
