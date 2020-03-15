package com.uniovi.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	
	public Page<FriendRequest> getFriendRequests(Pageable page, User user) {
		Page<FriendRequest> friendRequestList = new PageImpl<FriendRequest>(new ArrayList<FriendRequest>());
		friendRequestList = friendRequestRepository.findRequestByUser(page, user);
		return friendRequestList;
	}

	public void deleteFriendRequest(User userSender, User userReciever) {
		FriendRequest request = friendRequestRepository.findByUsers(userSender, userReciever);
		friendRequestRepository.delete(request);
	}

	public boolean isAlreadySent(User userSender, User userReciever) {
		FriendRequest request = friendRequestRepository.findByUsers(userSender, userReciever);
		return request != null;
	}
}
