package de.learny.domain;

import java.util.HashSet;
import java.util.Set;

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

	private String subjectName;

	@ManyToMany(mappedBy = "subjects")
	private Set<Account> accounts = new HashSet<Account>();

	@OneToMany(mappedBy = "subject")
	private Set<Test> tests;

	public Subject(String subjectName) {
		this.setSubjectName(subjectName);
	}

	public Subject() {

	}

	public long getId() {
		return id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@JsonIgnore
	public Set<Test> getTests() {
		return tests;
	}

	public void setTests(Set<Test> tests) {
		this.tests = tests;
	}
	
	@JsonIgnore
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

}
