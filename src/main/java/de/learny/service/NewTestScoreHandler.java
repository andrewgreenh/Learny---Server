package de.learny.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.learny.dataaccess.AnswerRepository;
import de.learny.dataaccess.TestScoreRepository;
import de.learny.domain.Answer;
import de.learny.domain.Question;
import de.learny.domain.TestScore;

@Service
public class NewTestScoreHandler {

	@Autowired
	TestScoreRepository testScoreRepo;

	@Autowired
	AnswerRepository answerRepo;

	public void addNew(TestScore testScore, Set<Question> questions) {
		testCalculate(testScore,questions);
	}

	private int calculateScore(int score, int answers) {
		int result = score * 100 / answers;
		return result;
	}
	private void testCalculate(TestScore testScore,Set<Question> questions){
		int score = 0;
		int answerCount = 0;
		for(Question question: questions){
			int points = 0;
			int checked = 0;
			int count = 0;
			int correctChecked = 0;
			for(Answer a: question.getAnswers()){
				count++;
				answerCount++;
				Answer answer = answerRepo.findById(a.getId());
				if(a.isCorrect()){
					checked++;
				}
				if(a.isCorrect() == answer.isCorrect()){
					points++;
					if(a.isCorrect()){
						correctChecked++;
					}
				}
				else{
					answer.addToErrorCount();
				}
				answer.addToAnswerCount();
				answerRepo.save(answer);
			}
			//Wenn nichts, alles oder keine einzige Antwort richtig angekreuzt
			//--> dann gibt es 0 Punkte für diese eine Question
			if(checked == 0 || checked == count || correctChecked == 0){
				points = 0;
			}
			score = score + points;
		}
		testScore.setScore(calculateScore(score, answerCount));
		testScoreRepo.save(testScore);
	}

}
