package de.learny.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Achievement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String achievementName;

	public Achievement(String achievementName) {
		this.setAchievementName(achievementName);
	}
	
	public Achievement() {
		
	}
	
	public long getId() {
		return id;
	}


	public String getAchievementName() {
		return achievementName;
	}

	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}
	
	
	
}
