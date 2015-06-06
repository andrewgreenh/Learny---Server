package de.learny.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String question;

	@OneToMany(mappedBy = "question")
	private Set<Answer> answers;

	@ManyToOne
	private Test test;

	public Question() {
		
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public long getId() {
		return id;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public Test getTest() {
		return test;
	}
	
	
}
