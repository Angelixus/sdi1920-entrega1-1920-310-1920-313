package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void addUser(User user) {
		// Password encrypt not yet
		userRepository.save(user);
	}
	
}
