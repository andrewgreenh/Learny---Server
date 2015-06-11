package de.learny.dataaccess;

import org.springframework.data.repository.CrudRepository;

import de.learny.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findFirstByName(String roleName);
	
	Iterable<Role> findAll();
	
	Role findById(long id);
}
