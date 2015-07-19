package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.PasswordResetToken;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);
	
}
