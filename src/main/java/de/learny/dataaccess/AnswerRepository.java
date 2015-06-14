package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Answer;


public interface AnswerRepository extends CrudRepository<Answer, Long> {
	
	Answer findFirstByAnswer(String answer);
	
	Iterable<Answer> findAll();
	
	Answer findById(long id);
}
