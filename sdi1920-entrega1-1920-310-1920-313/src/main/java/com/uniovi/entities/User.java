package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private String role;

	private String password = "";
	@Transient
	private String passwordConfirm = "";

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Set<FriendRequest> sentRequests;

	@OneToMany(mappedBy = "reciever", cascade = CascadeType.ALL)
	private Set<FriendRequest> recievedRequest;
	
	@OneToMany(mappedBy = "friendA", cascade = CascadeType.ALL)
	private Set<Friend> friendsIsA;
	
	@OneToMany(mappedBy = "friendB", cascade = CascadeType.ALL)
	private Set<Friend> friendsIsB;

	public User(String email) {
		if (email != "" && email != null)
			this.email = email;
		else
			throw new IllegalArgumentException("Email cannot be empty or null");
	}

	public User(String email, String name, String lastName) {
		this(email);
		this.name = name;
		this.lastName = lastName;
	}

	public User(String email, String name, String lastName, Set<FriendRequest> sentRequests,
			Set<FriendRequest> recievedRequests, Set<Friend> friendsIsA, Set<Friend> friendsIsB) {
		this(email, name, lastName);
		this.sentRequests = new HashSet<FriendRequest>(sentRequests);
		this.recievedRequest = new HashSet<FriendRequest>(recievedRequests);
		this.friendsIsA = new HashSet<Friend>(friendsIsA);
		this.friendsIsB = new HashSet<Friend>(friendsIsB);
	}

	public User() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Set<FriendRequest> getSentRequests() {
		return new HashSet<FriendRequest>(this.sentRequests);
	}

	public Set<FriendRequest> getRecievedRequests() {
		return new HashSet<FriendRequest>(this.recievedRequest);
	}
	
	public Set<Friend> getFriendsIsA() {
		return new HashSet<Friend>(this.friendsIsA);
	}
	
	public Set<Friend> getFriendsIsB() {
		return new HashSet<Friend>(this.friendsIsB);
	}

	public boolean didSentRequestToOther(User user) {
		boolean res = false;
		for (FriendRequest request : this.sentRequests) {
			if (request.getReciever().getEmail().equals(user.getEmail())) {
				res = true;
				break;
			}
		}

		return res;
	}

	public boolean didRecieveRequestFromLogged() {	
		Object logged = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = "";
		if (logged instanceof UserDetails)
			email = ((UserDetails) logged).getUsername();
		else
			email = logged.toString();
		boolean res = false;

		for (FriendRequest request : this.recievedRequest) {
			if (request.getSender().getEmail().equals(email)) {
				res = true;
				break;
			}
		}
		
		return res;
	}
	
	public boolean isAlreadyFriend() {
		Object logged = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = "";
		if (logged instanceof UserDetails)
			email = ((UserDetails) logged).getUsername();
		else
			email = logged.toString();
		boolean res = false;
		
		for (Friend friend : this.friendsIsA) {
			if (friend.getFriendA().getEmail().equals(email) || friend.getFriendB().getEmail().equals(email)) {
				res = true;
				break;
			}
		}	
		for (Friend friend : this.friendsIsB) {
			if (friend.getFriendA().getEmail().equals(email) || friend.getFriendB().getEmail().equals(email)) {
				res = true;
				break;
			}
		}	
		return res;
	}
	
}
