package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friend;
import com.uniovi.entities.User;

public interface FriendRepository extends CrudRepository<Friend, Long>{

	@Query("SELECT f FROM Friend f WHERE f.friendA LIKE ?1 OR f.friendB Like ?1")
	Page<Friend> findFriends(Pageable pageable, User loggedUser);

}
