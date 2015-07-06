package de.learny.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.learny.dataaccess.TestRepository;
import de.learny.domain.Answer;
import de.learny.domain.TestScore;

@Service
public class TestScoreCalculator {

	private TestScore testScore;

	@Autowired
	TestRepository testRepo;
	
	public Map<String, Integer> calculateRightAnswers() {
		Map<String,Integer> result = new HashMap<String,Integer>();
		int answerCount = 0;
		int errorCount = 0;
		for(Answer checkedAnswer: testScore.getCheckedAnswers()){
			if(!checkedAnswer.isCorrect()) {
				errorCount++;
				checkedAnswer.addToErrorCount();
			} 
			answerCount++;
			checkedAnswer.addToAnswerCount();
		}
		
		for(Answer uncheckedAnswer: testScore.getUncheckedAnswers()){
			if(uncheckedAnswer.isCorrect()) {
				errorCount++;
				uncheckedAnswer.addToErrorCount();
			} 
			answerCount++;
			uncheckedAnswer.addToAnswerCount();
		}
		
		testRepo.save(testScore.getTest());
		
		int score = calculateScore(answerCount, errorCount);
		result.put("Fehler", errorCount);
		result.put("Antworten abgegeben", answerCount);
		result.put("score", score);
		return result;
		
	}

	public TestScore getTestScore() {
		return testScore;
	}

	public void setTestScore(TestScore testScore) {
		this.testScore = testScore;
	}

	private int calculateScore(int answers, int errors) {
		int result = (answers - errors) * 100 / answers ;
		return result;
	}
	
}
