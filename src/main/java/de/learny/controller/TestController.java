package de.learny.controller;

import io.swagger.annotations.Api;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import de.learny.JsonView.View;
import de.learny.controller.exception.NotEnoughPermissionsException;
import de.learny.controller.exception.ResourceNotFoundException;
import de.learny.dataaccess.TestRepository;
import de.learny.dataaccess.TestScoreRepository;
import de.learny.domain.Account;
import de.learny.domain.Answer;
import de.learny.domain.Question;
import de.learny.domain.Subject;
import de.learny.domain.Test;
import de.learny.domain.TestScore;
import de.learny.security.service.LoggedInAccountService;
import de.learny.service.NewTestScoreHandler;

@Api(value = "Tests", description = "Tests verwalten. Test ausfüllen. Testergebnisse einsehen", produces = "application/json")
@RestController
@RequestMapping("/api/tests")
public class TestController {

	@Autowired
	private TestRepository testRepository;

	@Autowired
	private LoggedInAccountService userToAccountService;

	@Autowired
	private TestScoreRepository testScoreRepo;

	@Autowired
	private NewTestScoreHandler newTestScoreHandler;

	@RequestMapping(value = "", method = RequestMethod.GET)
	Iterable<Test> getAllTests() {
		return testRepository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Test getTest(@PathVariable("id") long id) {
		Test test = testRepository.findById(id);
		if (test == null)
			throw new ResourceNotFoundException("Ein Test mit diese id existiert nicht");
		return test;
	}

	public TestRepository getTestRepository() {
		return testRepository;
	}

	public void setTestRepository(TestRepository testRepository) {
		this.testRepository = testRepository;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") long id) {
		Test test = testRepository.findById(id);
		if (test == null)
			throw new ResourceNotFoundException("Ein Test mit diese id existiert nicht");
		if (permitted(id)) {
			this.testRepository.delete(id);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	Test update(@PathVariable("id") long id, @RequestBody Test updateTest) {
		// TODO: Was muss noch geupdatet werden?
		Test oldTest = testRepository.findById(id);
		if (oldTest == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		if (permitted(id)) {
			oldTest.setName(updateTest.getName());
		}
		return this.testRepository.save(oldTest);
	}

	@JsonView(View.Summary.class)
	@RequestMapping(value = "/{id}/questions", method = RequestMethod.GET)
	Iterable<Question> getAllQuestionsToTest(@PathVariable("id") long id) {
		Test test = testRepository.findById(id);
		if (test == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		return test.getQuestions();
	}

	@RequestMapping(value = "/{id}/results", method = RequestMethod.GET)
	Iterable<TestScore> getResultsFromTest(@PathVariable("id") long id) {
		Test test = testRepository.findById(id);
		if (test == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		return test.getTestScores();
	}

	@RequestMapping(value = "/{id}/results", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	void turnTest(@PathVariable("id") long id,
	        @RequestBody Set<Answer> checkedAnswers) {
		Test test = testRepository.findById(id);
		if (test == null)
			throw new ResourceNotFoundException("Ein Test mit dieser id existiert nicht");
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		TestScore oldTestScore =  testScoreRepo.findByAccountAndTest(loggedInAccount, test);
		if(oldTestScore != null){
			testScoreRepo.delete(oldTestScore);
		}
		
		TestScore testScore = new TestScore(test, loggedInAccount, checkedAnswers);
		newTestScoreHandler.addNew(testScore);
	}

	@JsonView(View.Summary.class)
	@RequestMapping(value = "/{id}/highscore", method = RequestMethod.GET)
	Iterable<TestScore> getHighscoreFromTest(@PathVariable("id") long id) {
		Test test = testRepository.findById(id);
		if (test == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		return testScoreRepo.findTop10ByTestOrderByScoreDesc(test);
	}

	@RequestMapping(value = "/{id}/myLatestResult", method = RequestMethod.GET)
	TestScore myLatestResult(@PathVariable("id") long id) {
		Test test = testRepository.findById(id);
		if (test == null)
			throw new ResourceNotFoundException("Ein Fach mit dieser id existiert nicht");
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		return testScoreRepo.findFirstByAccountAndTestOrderByTimestampDesc(loggedInAccount, test);
	}

	private boolean permitted(long id) {
		Test test = testRepository.findById(id);
		Account loggedInAccount = userToAccountService.getLoggedInAccount();
		boolean inCharge = false;
		Subject subject = test.getSubject();
		inCharge = subject.getAccountsInCharge().contains(loggedInAccount);
		if (inCharge || loggedInAccount.hasRole("admin")) {
			return true;
		} else {
			throw new NotEnoughPermissionsException("Nicht genug Rechte, um das auszuführen.");
		}
	}
}
