package de.learny.domain;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String subjectName;
	
	@OneToMany
	private Set<Test> test;
	
	public Subject(String subjectName) {
		this.setSubjectName(subjectName);
	}
	
	public Subject() {
		
	}
	
	public long getId() {
		return id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}
