package com.uniovi.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UserRepository;

@Service
public class UserService {

	
	@Autowired
	private UserRepository usersRepository;
	
	@Autowired
	private RolesService rolesService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Page<User> getUsers(Pageable pageable) {
		Page<User> users = new PageImpl<User>(new ArrayList<User>());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		users = usersRepository.findAllExceptUserAndAdmin(pageable, auth.getName(), rolesService.getRoles()[1]);
		return users;
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}
	
	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public void addEncryptelessUser(User previous) {
		usersRepository.save(previous);
	}

	public Page<User> searchUsersByNameSurnameOrEmail(Pageable pageable, String searchText){
		Page<User> users = new PageImpl<User>(new ArrayList<User>());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		searchText = "%"+searchText+"%";
		users = usersRepository.findByNameSurnameOrEmail(pageable, searchText);
		
		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			User user = it.next();
			if(user.getEmail().equals(auth.getName()) || user.getRole().equals(rolesService.getRoles()[1])) {
				it.remove();
			}
		}
		return users;
	}	
}
