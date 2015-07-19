package de.learny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class LearnyMailSender {

	@Autowired
	private MailSender mailSender;

	public void sendMail(String recipient, String subject, String message) {
		mailSender.send(constructEmail(recipient, subject, message));
	}

	private SimpleMailMessage constructEmail(String recipient, String subject,
	        String message) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipient);
		email.setSubject(subject);
		email.setText(message);
		email.setFrom("learny@learny.xent-online.de");
		return email;
	}

}
