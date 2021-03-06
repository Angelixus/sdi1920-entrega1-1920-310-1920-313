package com.uniovi.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;
import com.uniovi.services.FriendRequestService;
import com.uniovi.services.FriendService;
import com.uniovi.services.UserService;

@Controller
public class FriendRequestController {

	@Autowired
	private FriendRequestService friendRequestService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendService friendService;
	
	@RequestMapping("/friendRequest/list")
	public String getListado(Model model, Pageable pageable, @RequestParam(value="",required = false) String searchText) {
		Page<FriendRequest> friendRequests = new PageImpl<FriendRequest>(new ArrayList<FriendRequest>());
		Object loggedEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(loggedEmail instanceof UserDetails)
			loggedEmail = ((UserDetails)loggedEmail).getUsername();
		else
			loggedEmail = loggedEmail.toString();
			
		User loggedUser = userService.getUserByEmail((String) loggedEmail);
		if (searchText != null && !searchText.isEmpty()) {
			//users = friendRequestService.searchUsersByNameOrEmail(pageable, searchText);
		}else {
			friendRequests = friendRequestService.getFriendRequests(pageable, loggedUser);
		}
		
		model.addAttribute("friendRequestList", friendRequests.getContent());	
		model.addAttribute("page", friendRequests);
		return "friendRequest/list";
	}
	
	@RequestMapping(value = "/friendRequest/list/update")
	public String updateList(Model model, Pageable pageable) {
		Object loggedEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(loggedEmail instanceof UserDetails)
			loggedEmail = ((UserDetails)loggedEmail).getUsername();
		else
			loggedEmail = loggedEmail.toString();
			
		User loggedUser = userService.getUserByEmail((String) loggedEmail);
		model.addAttribute("friendRequestList", friendRequestService.getFriendRequests(pageable, loggedUser).getContent());
		return "friendRequest/list :: tablefriendRequest";
	}
	
	@RequestMapping(value = "/friendRequest/{id}/accept", method = RequestMethod.GET)
	public String acceptRequest(Model model, @PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if(principal instanceof UserDetails)
			username = ((UserDetails)principal).getUsername();
		else
			username = principal.toString();
		User userAFriend = userService.getUser(id);
		User userBFriend = userService.getUserByEmail(username);
		
		friendRequestService.deleteFriendRequest(userAFriend, userBFriend);
		friendService.createFriends(userAFriend, userBFriend);
		return "redirect:/user/list";
	}
	
}
