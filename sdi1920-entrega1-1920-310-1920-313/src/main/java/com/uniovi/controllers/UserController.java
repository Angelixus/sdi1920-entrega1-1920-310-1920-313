package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.User;

@Controller
public class UserController {

	@RequestMapping(value="/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
}
