package de.learny.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String answer;
	
	@ManyToOne
	private Question question;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@JsonIgnore
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public long getId() {
		return id;
	}

	
}
