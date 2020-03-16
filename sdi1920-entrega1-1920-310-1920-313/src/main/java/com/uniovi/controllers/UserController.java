package com.uniovi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.FriendRequestService;
import com.uniovi.services.FriendService;
import com.uniovi.services.PublicationService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UserService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UserController {

	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private FriendRequestService friendRequestService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private PublicationService publicationService;
	
	@Autowired
	private FriendService friendService;
	
	@RequestMapping(value="/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if(result.hasErrors())
			return "signup";
		
		user.setRole(rolesService.getRoles()[0]);
		userService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		
		return "redirect:home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error) {
		if(error != null)
			model.addAttribute("errors", true);
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable, @RequestParam(value="",required = false) String searchText) {
		Page<User> users = new PageImpl<User>(new ArrayList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			users = userService.searchUsersByNameSurnameOrEmail(pageable, searchText);
		}else {
			users = userService.getUsers(pageable);
		}
		
		model.addAttribute("usersList", users.getContent());	
		model.addAttribute("page", users);
		return "user/list";
	}
	
	@RequestMapping(value = "/user/list/update")
	public String updateList(Model model, Pageable pageable) {
		model.addAttribute("usersList", userService.getUsers(pageable).getContent());
		return "user/list :: tableUsers";
	}
	
	@RequestMapping(value = "/user/{id}/send", method = RequestMethod.GET)
	public String sendRequest(Model model, @PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if(principal instanceof UserDetails)
			username = ((UserDetails)principal).getUsername();
		else
			username = principal.toString();
		
		User userSender = userService.getUserByEmail(username);
		User userReciever = userService.getUser(id);
		if(friendRequestService.isAlreadySent(userSender, userReciever)) {
			return "redirect:/user/list";
		} else {
			friendRequestService.sendFriendRequest(userSender, userReciever);
			return "redirect:/user/list";
		}
	}
	
	@RequestMapping(value="/user/deleteChecked")
	public String deleteUser(Model model, @RequestParam List<Long> ids, Pageable pageable) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if(principal instanceof UserDetails)
			username = ((UserDetails)principal).getUsername();
		else
			username = principal.toString();
		
		for(Long id : ids) {
			User user = userService.getUser(id);			
			if(!user.getEmail().equals(username)) {
				publicationService.deletePublicationsOfUser(user);
				friendService.deleteFriendUser(user);
				friendRequestService.deleteFriendRequestUser(user);
				userService.deleteUser(user);	
			}
		}
		model.addAttribute("usersList", userService.getUsers(pageable).getContent());
		return "user/list :: tableUsers";
	}

}
