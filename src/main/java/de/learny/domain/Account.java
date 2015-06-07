package de.learny.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique=true)
	private String accountName;
	
	private String password, surname, lastname, email, avatarUri;
	
	@ManyToOne
	private Role role;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
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
	
	@JsonProperty
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
		return Collections.unmodifiableSet(administratedSubjects);
	}
	
	public boolean addAdministratedSubject(Subject subject) {
		this.administratedSubjects.add(subject);
		if (!subject.getAccountsInCharge().contains(this)) {
			subject.addAccountInCharge(this);
		}
		return true;
	}
	
	public boolean removeAdministratedSubject(Subject subject) {
		this.administratedSubjects.remove(subject);
		if(subject.getAccountsInCharge().contains(this)) {
			subject.removeAccountInCharge(this);
		}
		return true;
	}

	public Set<Achievement> getAchievements() {
		return Collections.unmodifiableSet(achievements);
	}
	
	public boolean addAchievement(Achievement achievement) {
		return this.achievements.add(achievement);
	}
	
	public boolean removeAchievement(Achievement achievement) {
		return this.achievements.remove(achievement);
	}

	@JsonIgnore
	public Set<Subject> getJoinedSubjects() {
		return Collections.unmodifiableSet(joinedSubjects);
	}

	public boolean addJoinedSubject(Subject subject) {
		this.joinedSubjects.add(subject);
		if(!subject.getParticipants().contains(this)) {
			subject.addParticipant(this);
		}
		return true;
	}
	
	public boolean removeJoinedSubject(Subject subject) {
		this.joinedSubjects.remove(subject);
		if(subject.getParticipants().contains(this)) {
			subject.removeParticipant(this);
		}
		return true;
	}
	
	

}
