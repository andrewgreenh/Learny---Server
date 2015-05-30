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
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String accountName;
	private String password;
	
	@OneToMany(mappedBy="account")
	private Set<TestScore> testScores;
	
	@ManyToMany
	private Set<Subject> subjects = new HashSet<Subject>();

	public Account(String accountName, String password) {
		this.accountName = accountName;
		this.password = password;
	}
	
	public Account() {
		
	}
	
	public long getId() {
		return id;
	}

	public String getAccountName() {
		return accountName;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	
	
}
