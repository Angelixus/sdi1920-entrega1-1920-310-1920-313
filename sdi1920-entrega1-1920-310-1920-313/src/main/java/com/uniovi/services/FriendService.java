package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

	public Page<User> getFriends(Pageable pageable, User loggedUser) {
		Page<Friend> aux = new PageImpl<Friend>(new ArrayList<Friend>());
		aux = friendRepository.findFriends(pageable, loggedUser);
		List<User> users = aux.getContent().stream().map(friend -> {
			if(friend.getFriendA().getEmail().equals(loggedUser.getEmail()))
				return friend.getFriendB();
			else
				return friend.getFriendA();
		}).collect(Collectors.toList());
		return new PageImpl<User>(users, aux.getPageable(), aux.getTotalElements());
	}

	public void deleteFriendUser(User user) {
		List<Friend> friends = friendRepository.findFriendsNoPageable(user);
		for(Friend friend : friends) {
			friendRepository.delete(friend);
		}
	}

}
