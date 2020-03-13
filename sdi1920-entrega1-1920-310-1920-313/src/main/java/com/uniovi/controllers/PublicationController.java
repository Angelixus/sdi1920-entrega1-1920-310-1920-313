package com.uniovi.controllers;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Publication;
import com.uniovi.services.PublicationService;
import com.uniovi.services.UserService;

@Controller
public class PublicationController {

	@Autowired
	public PublicationService publicationService;
	
	@Autowired
	private UserService usersService;
	
	@RequestMapping(value = "/publication/add", method = RequestMethod.POST)
	public String setPublication(@ModelAttribute Publication publication) {
		Object poster = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = "";
		if (poster instanceof UserDetails)
			email = ((UserDetails) poster).getUsername();
		else
			email = poster.toString();
		boolean res = false;
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date creationDate = new Date(ts.getTime());
		
		publication.setPoster(usersService.getUserByEmail(email));
		publication.setDate(creationDate);
		publicationService.addPublication(publication);
		return "redirect:/publication/list";
	}

	@RequestMapping(value = "/publication/add")
	public String getPublication(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "publication/add";
	}
	
}
