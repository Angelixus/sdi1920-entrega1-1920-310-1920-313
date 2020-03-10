package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
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
	private SecurityService securityService;
	
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
			model.addAttribute("errors", "Email and/or password are invalid");
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping("/user/list")
	public String getListado(Model model, @RequestParam(value="",required = false) String searchText) {

		if (searchText != null && !searchText.isEmpty()) {
			model.addAttribute("usersList", userService.searchUsersByNameOrEmail(searchText));
		}else {
			model.addAttribute("usersList", userService.getUsers());	
		}
		
		return "user/list";
	}

}
