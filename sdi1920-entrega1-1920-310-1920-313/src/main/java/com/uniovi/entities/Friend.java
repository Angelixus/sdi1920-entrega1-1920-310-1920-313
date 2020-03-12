package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friend")
public class Friend {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User friendA;
	
	@ManyToOne
	private User friendB;
	
	public Friend() {
		// TODO Auto-generated constructor stub
	}
	
	public Friend(User friendA, User friendB) {
		this.friendA = friendA;
		this.friendB = friendB;
	}
	
	public User getFriendA() {
		return friendA;
	}
	
	public User getFriendB() {
		return friendB;
	}
}
