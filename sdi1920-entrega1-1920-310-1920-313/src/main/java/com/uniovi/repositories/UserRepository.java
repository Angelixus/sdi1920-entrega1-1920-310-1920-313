package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.email) LIKE LOWER(?1))")
	public Page<User> findByNameOrEmail(Pageable pageable, String name);

	@Query("SELECT u FROM User u WHERE (LOWER(u.email) NOT LIKE LOWER(?1) AND LOWER(u.role) NOT LIKE LOWER(?2))")
	Page<User> findAllExceptUserAndAdmin(Pageable pageable, String name, String string);
}
