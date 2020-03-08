package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);
}
