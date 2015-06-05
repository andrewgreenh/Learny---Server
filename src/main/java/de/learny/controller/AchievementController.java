package de.learny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.learny.controller.exception.ResourceNotFoundException;
import de.learny.dataaccess.AchievementRepository;
import de.learny.domain.Achievement;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {
	
	@Autowired
	private AchievementRepository achievmentRepo;
	
	@RequestMapping(method = RequestMethod.GET)
	Iterable<Achievement> getAllSubject() {
		return achievmentRepo.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Achievement getSubject(@PathVariable("id") long id) {
		Achievement achievement = achievmentRepo.findById(id);
		if (achievement == null)
			throw new ResourceNotFoundException();
		return achievement;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	void create(@RequestBody Achievement achievement){
		//TODO: Nur der Admin darf es
		this.achievmentRepo.save(achievement);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
	Achievement update(@PathVariable("id") long id, @RequestBody Achievement updateAchievement){
		//TODO: Funktioniert nur wenn in der JSON-Datei die ID steht. Außerdem ist es kein update mehr sondern ein überschreiben
		//Subject subject = this.subjectRep.findById(id);
		//BeanUtils.copyProperties(subject, updateSubject);
		return this.achievmentRepo.save(updateAchievement);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") long id){
		//TODO: Nur der Admin darf es
		this.achievmentRepo.delete(id);
	}
	
	

}
