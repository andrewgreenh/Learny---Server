package de.learny.domain;


import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PasswordResetToken {

	private static final int EXPIRATION = 1000 * 60 * 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	private long expiryDate;

	public PasswordResetToken() {
		token = UUID.randomUUID().toString();
		expiryDate = new Date().getTime() + EXPIRATION;
	}
	
	public static int getExpiration() {
		return EXPIRATION;
	}

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public long getExpiryDate() {
		return expiryDate;
	}
	
	

}
