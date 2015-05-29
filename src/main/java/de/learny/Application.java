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
import de.learny.dataaccess.TestRepository;
import de.learny.domain.Account;
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
	AccountRepository repository;
	
	@Autowired
	TestRepository testRepo;
	
	@Autowired
	PasswordGeneratorService passwordGenerator;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    	testRepo.save(new Test("test1"));
    	testRepo.save(new Test("test2"));
    	
        // save a couple of customers
		repository.save(new Account("admin", passwordGenerator.hashPassword("admin")));
		repository.save(new Account("student", passwordGenerator.hashPassword("student")));
		repository.save(new Account("dozent", passwordGenerator.hashPassword("dozent")));
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
