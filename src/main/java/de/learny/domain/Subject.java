package de.learny.domain;

import java.util.Collections;
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

	@ManyToMany(mappedBy = "administratedSubjects", cascade = CascadeType.ALL)
	private Set<Account> accountsInCharge = new HashSet<Account>();

	@ManyToMany(mappedBy = "joinedSubjects", cascade = CascadeType.ALL)
	private Set<Account> participants = new HashSet<Account>();

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
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
		return Collections.unmodifiableSet(accountsInCharge);
	}
	
	public boolean addAccountInCharge(Account account) {
		this.accountsInCharge.add(account);
		if(!account.getAdministratedSubjects().contains(this)) {
			account.addAdministratedSubject(this);
		}
		return true;
	}
	
	public boolean removeAccountInCharge(Account account) {
		this.accountsInCharge.remove(account);
		if(account.getAdministratedSubjects().contains(account)) {
			account.removeAdministratedSubject(this);
		}
		return true;
	}

	@JsonIgnore
	public Set<Account> getParticipants() {
		return Collections.unmodifiableSet(participants);
	}
	
	public boolean addParticipant(Account account) {
		this.participants.add(account);
		if(!account.getJoinedSubjects().contains(this)) {
			account.addJoinedSubject(this);
		}
		return true;
	}
	
	public boolean removeParticipant(Account account) {
		this.participants.remove(account);
		if(account.getJoinedSubjects().contains(this)) {
			account.removeJoinedSubject(this);
		}
		return true;
	}

	@JsonIgnore
	public Set<Test> getTests() {
		return Collections.unmodifiableSet(tests);
	}
	
	public boolean addTest(Test test) {
		this.tests.add(test);
		if(test.getSubject() != this) {
			test.setSubject(this);
		}
		return true;
	}
	
	public boolean removeTest(Test test) {
		this.tests.remove(test);
		if(test.getSubject() == this) {
			test.setSubject(null);
		}
		return true;
	}

}
