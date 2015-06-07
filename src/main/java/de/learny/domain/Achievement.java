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

	private String name, description, badgeUri;

	public Achievement(String achievementName) {
		this.name = achievementName;
	}

	public Achievement() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBadgeUri() {
		return badgeUri;
	}

	public void setBadgeUri(String badgeUri) {
		this.badgeUri = badgeUri;
	}

	public long getId() {
		return id;
	}

}
