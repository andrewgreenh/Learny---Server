package de.learny.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String accountName;
	
	private String password, surname, lastname, email, avatarUri;
	
	@ManyToOne
	private Role role;

	@OneToMany(mappedBy = "account")
	private Set<TestScore> testScores = new HashSet<TestScore>();

	@ManyToMany
	private Set<Subject> administratedSubjects = new HashSet<Subject>();
	
	@OneToMany
	private Set<Achievement> achievements = new HashSet<Achievement>();
	
	@ManyToMany
	private Set <Subject> joinedSubjects = new HashSet<Subject>();

	
	public Account(String accountName, String password) {
		this.accountName = accountName;
		this.password = password;
	}

	public Account() {

	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatarUri() {
		return avatarUri;
	}

	public void setAvatarUri(String avatarUri) {
		this.avatarUri = avatarUri;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public String getAccountName() {
		return accountName;
	}
	
	@JsonIgnore
	public Set<TestScore> getTestScores() {
		return testScores;
	}

	@JsonIgnore
	public Set<Subject> getAdministratedSubjects() {
		return administratedSubjects;
	}

	public Set<Achievement> getAchievements() {
		return achievements;
	}

	@JsonIgnore
	public Set<Subject> getJoinedSubjects() {
		return joinedSubjects;
	}

	

}
