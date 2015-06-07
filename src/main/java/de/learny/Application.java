package de.learny;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.learny.dataaccess.AccountRepository;
import de.learny.dataaccess.QuestionRepository;
import de.learny.dataaccess.SubjectRepository;
import de.learny.dataaccess.TestRepository;
import de.learny.domain.Account;
import de.learny.domain.Subject;
import de.learny.domain.Test;
import de.learny.security.service.PasswordGeneratorService;



/**
 * Acts as a servlet initializer and start class.
 * 
 * @author andi
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer implements CommandLineRunner{
	
	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	TestRepository testRepo;
	
	@Autowired
	SubjectRepository subjectRepo;
	
	@Autowired 
	QuestionRepository questionRepo;
	
	@Autowired
	PasswordGeneratorService passwordGenerator;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    	Subject sub1 = new Subject("Fach1");
    	Subject sub2 = new Subject("Fach2");
    	subjectRepo.save(sub1);
    	subjectRepo.save(sub2);
    	testRepo.save(new Test("test1", sub1));
    	testRepo.save(new Test("test3", sub1));
    	testRepo.save(new Test("test2", sub2));
    	
    	Account student = new Account("student", passwordGenerator.hashPassword("student"));
    	Account admin = new Account("admin", passwordGenerator.hashPassword("admin"));
    	Account dozent = new Account("dozent", passwordGenerator.hashPassword("dozent"));
    	accountRepo.save(admin);
    	accountRepo.save(student);
    	accountRepo.save(dozent);
    	
    	sub1.addAccountInCharge(dozent);
    	subjectRepo.save(sub1);
    	student.addJoinedSubject(sub1);
    	accountRepo.save(student);
    	
    	
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
