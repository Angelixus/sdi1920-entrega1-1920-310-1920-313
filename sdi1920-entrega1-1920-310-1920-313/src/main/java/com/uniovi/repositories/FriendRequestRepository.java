package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long>{

}