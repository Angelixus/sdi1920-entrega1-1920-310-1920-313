package com.uniovi.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.PublicationService;
import com.uniovi.services.UserService;
import com.uniovi.validators.CreatePublicationValidator;

@Controller
public class PublicationController {

	@Autowired
	public PublicationService publicationService;

	@Autowired
	private UserService usersService;

	@Autowired
	private CreatePublicationValidator pubValidator;

	@RequestMapping(value = "/publication/add", method = RequestMethod.POST)
	public String setPublication(@ModelAttribute @Validated Publication publication, BindingResult result,
			@RequestParam("image") MultipartFile image) {
		pubValidator.validate(publication, result);
		if (result.hasErrors())
			return "/publication/add";

		Object poster = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = "";
		if (poster instanceof UserDetails)
			email = ((UserDetails) poster).getUsername();
		else
			email = poster.toString();

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date creationDate = new Date(ts.getTime());

		publicationService.addPublication(publication, usersService.getUserByEmail(email), creationDate);
		publicationService.addImageToPublication(publication, image);

		return "redirect:/user/list";
	}

	@RequestMapping(value = "/publication/add")
	public String getPublication(Model model) {
		model.addAttribute("publication", new Publication());
		return "publication/add";
	}

	@RequestMapping("/publication/list")
	public String getList(Model model) {
		Object poster = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = "";
		if (poster instanceof UserDetails)
			email = ((UserDetails) poster).getUsername();
		else
			email = poster.toString();

		User user = usersService.getUserByEmail(email);
		List<Publication> publications = new ArrayList<>();
		publications = publicationService.getPublicationsForUser(user);

		model.addAttribute("myPublicationsList", publications);
		return "publication/list";
	}

	@RequestMapping("/publication/list/{id}")
	public String getPublicationListOfFriend(Model model, @PathVariable Long id) {		
		User userNotLogged = usersService.getUser(id);
		if (usersService.areFriendsOrIsLogged(userNotLogged)) {
			List<Publication> publications = new ArrayList<Publication>();
			publications = publicationService.getPublicationsForUser(userNotLogged);

			model.addAttribute("myPublicationsList", publications);
			return "publication/list";
		} else {
			return "redirect:/forbidden";
		}
	}

}
