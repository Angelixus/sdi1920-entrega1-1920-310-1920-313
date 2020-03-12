package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friend;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRepository;

@Service
public class FriendService {

	@Autowired
	private FriendRepository friendRepository;
	
	public void createFriends(User userAFriend, User userBFriend) {
		Friend friendEntity = new Friend(userAFriend, userBFriend);
		friendRepository.save(friendEntity);
		
	}

}
