package de.learny.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String testName;
	
	@ManyToOne
	private Subject subject;

	public Test(String testName) {
		this.setTestName(testName);
	}
	
	public Test() {
		
	}
	
	public long getId() {
		return id;
	}


	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	
	
}
