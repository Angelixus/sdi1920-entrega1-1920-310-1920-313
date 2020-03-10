package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friendrequest")
public class FriendRequest {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User sender;
	
	@ManyToOne
	private User reciever;
	
	public FriendRequest() {
		
	}
	
	public FriendRequest(User sender, User reciever) {
		if(sender == null || reciever == null)
			throw new IllegalArgumentException("Sender and/or reciever cannot be null");
		
		this.sender = sender;
		this.reciever = reciever;
	}

	public User getSender() {
		return sender;
	}

	public User getReciever() {
		return reciever;
	}
}
