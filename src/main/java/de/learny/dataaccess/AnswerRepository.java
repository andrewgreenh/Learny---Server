package de.learny.dataaccess;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Answer;
import de.learny.domain.Question;


public interface AnswerRepository extends CrudRepository<Answer, Long> {
	
	Answer findFirstByAnswer(String answer);
	
	Iterable<Answer> findAll();
	
	Answer findById(long id);
	
}
