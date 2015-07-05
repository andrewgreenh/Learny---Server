package de.learny.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.learny.JsonView.View;

@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(View.Summary.class)
	private long id;

	@JsonView(View.Summary.class)
	private String answer;
	
	private boolean correct;
	
	@ManyToOne
	private Question question;
	
	public Answer(String answer, Question question, boolean correct){
		this.answer = answer;
		this.question = question;
		this.correct = correct;
	}
	
	public Answer(){
		
	}

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

	//@JsonIgnore
	public boolean isCorrect() {
		return correct;
	}

	@JsonProperty
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	
}
