package de.learny.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name, description;

	@ManyToMany(mappedBy = "administratedSubjects")
	private Set<Account> accountsInCharge = new HashSet<Account>();

	@ManyToMany(mappedBy = "joinedSubjects")
	private Set<Account> participants = new HashSet<Account>();

	@OneToMany(mappedBy = "subject")
	private Set<Test> tests = new HashSet<Test>();

	public Subject(String name) {
		this.name = name;
	}

	public Subject() {

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

	public long getId() {
		return id;
	}

	public Set<Account> getAccountsInCharge() {
		return accountsInCharge;
	}

	@JsonIgnore
	public Set<Account> getParticipants() {
		return participants;
	}

	@JsonIgnore
	public Set<Test> getTests() {
		return tests;
	}

}
