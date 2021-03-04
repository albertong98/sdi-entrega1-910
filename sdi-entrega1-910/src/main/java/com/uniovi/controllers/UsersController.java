package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.User;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute User user) {
		user.setSaldo(100);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(),user.getPasswordConfirm());
		return "redirect:home";
	}
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
}
