package de.learny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.learny.dataaccess.AnswerRepository;
import de.learny.dataaccess.TestScoreRepository;
import de.learny.domain.Answer;
import de.learny.domain.TestScore;

@Service
public class NewTestScoreHandler {

	@Autowired
	TestScoreRepository testScoreRepo;

	@Autowired
	AnswerRepository answerRepo;

	public void addNew(TestScore testScore) {
		calculateScore(testScore);
	}

	private void calculateScore(TestScore testScore) {
		int answerCount = 0;
		int errorCount = 0;
		for (Answer checkedAnswer : testScore.getCheckedAnswers()) {
			Answer answer = answerRepo.findById(checkedAnswer.getId());
			if (!answer.isCorrect()) {
				errorCount++;
				answer.addToErrorCount();
			}
			answerCount++;
			answer.addToAnswerCount();
			answerRepo.save(answer);
		}

		for (Answer uncheckedAnswer : testScore.getUncheckedAnswers()) {
			Answer answer = answerRepo.findById(uncheckedAnswer.getId());
			if (answer.isCorrect()) {
				errorCount++;
				answer.addToErrorCount();
			}
			answerCount++;
			answer.addToAnswerCount();
			answerRepo.save(answer);
		}

		testScore.setScore(calculateScore(answerCount, errorCount));
		testScoreRepo.save(testScore);
	}

	private int calculateScore(int answers, int errors) {
		int result = (answers - errors) * 100 / answers;
		return result;
	}

}
