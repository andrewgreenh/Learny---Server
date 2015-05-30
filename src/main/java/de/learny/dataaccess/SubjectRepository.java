package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
	
	Subject findFirstBySubjectName(String subjectName);
	
	Iterable<Subject> findAll();
	
	Subject findById(long id);

}
