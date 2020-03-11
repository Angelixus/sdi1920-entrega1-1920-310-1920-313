package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRequestRepository;

@Service
public class FriendRequestService {

	@Autowired
	private FriendRequestRepository friendRequestRepository;
	
	public void sendFriendRequest(User sender, User reciever) {
		FriendRequest request = new FriendRequest(sender, reciever);
		friendRequestRepository.save(request);
	}
}
