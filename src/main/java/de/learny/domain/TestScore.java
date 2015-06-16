package de.learny.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TestScore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany
	private Set<Answer> answers = new HashSet<Answer>();
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Test test;
	
	public TestScore(Test test, Account account, Test turnTest) {
		this.test = test;
		this.account = account;
	}
	
	public TestScore() {
		// TODO Auto-generated constructor stub
	}
	
	@JsonIgnore
	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@JsonIgnore
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
		if(!this.test.getTestScores().contains(this)){
			test.getTestScores().add(this);
		}
	}

	public long getId() {
		return id;
	}

	
}
