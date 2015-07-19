package de.learny.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import de.learny.JsonView.View;

@Entity
public class TestScore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(View.Summary.class)
	private long id;

	@ManyToMany
	private Set<Answer> checkedAnswers = new HashSet<Answer>();

	@ManyToMany
	private Set<Answer> uncheckedAnswers = new HashSet<Answer>();

	@ManyToOne
	@JsonView(View.Summary.class)
	private Account account;

	@ManyToOne
	@JsonView(View.Summary.class)
	private Test test;

	@JsonView(View.Summary.class)
	private Timestamp timestamp;
	
	@JsonView(View.Summary.class)
	private int score;

	public TestScore(Test test, Account account, Set<Question> questions) {
		setTest(test);
		this.account = account;
		setUnAndCheckedAnswers(questions);
		java.util.Date currentDate = new java.util.Date();
		this.timestamp = new Timestamp(currentDate.getTime());
	}

	public TestScore() {
		// TODO Auto-generated constructor stub
	}

	// @JsonIgnore
	public Set<Answer> getCheckedAnswers() {
		return checkedAnswers;
	}

	public Set<Answer> getUncheckedAnswers() {
		return uncheckedAnswers;
	}

	public void setCheckedAnswers(Set<Answer> answers) {
		this.checkedAnswers = answers;
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
		if (!this.test.getTestScores().contains(this)) {
			test.addTestScore(this);
		}
	}

	public long getId() {
		return id;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	private void setUnAndCheckedAnswers(Set<Question> questions) {
		Set<Answer> checkedAnswers = new HashSet<>();
		Set<Answer> uncheckedAnswers = new HashSet<>();
		for(Question question: questions){
			for(Answer answer: question.getAnswers()){
				if(answer.isCorrect()){
					checkedAnswers.add(answer);
				}
				else{
					uncheckedAnswers.add(answer);
				}
			}
		}
		this.checkedAnswers = checkedAnswers;
		this.uncheckedAnswers = uncheckedAnswers;
	}

}
