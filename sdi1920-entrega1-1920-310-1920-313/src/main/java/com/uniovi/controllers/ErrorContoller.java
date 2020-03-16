package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorContoller {

	@RequestMapping(value="/forbidden")
	public String forbidden() {
		return "/errors/forbidden";
	}
}
