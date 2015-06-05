package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Achievement;

public interface AchievementRepository extends CrudRepository<Achievement, Long> {
	
	Iterable<Achievement> findAll();
	
	Achievement findById(long id);
	
}
