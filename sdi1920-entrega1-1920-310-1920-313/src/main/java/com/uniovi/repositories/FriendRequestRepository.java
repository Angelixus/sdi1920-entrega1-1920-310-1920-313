package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long>{

	@Query("SELECT fr FROM FriendRequest fr WHERE fr.reciever LIKE ?1")
	public Page<FriendRequest> findRequestByUser(Pageable pageable, User user);

	@Query("SELECT fr FROM FriendRequest fr WHERE (fr.sender LIKE ?1 AND fr.reciever LIKE ?2) OR (fr.sender LIKE ?2 AND fr.reciever LIKE ?1)")
	public FriendRequest findByUsers(User userSender, User userReciever);
	
	@Query("SELECT fr FROM FriendRequest fr WHERE (fr.reciever LIKE ?1 OR fr.sender LIKE ?1)")
	public List<FriendRequest> findRequestByUserNoPageable(User user);
	
}