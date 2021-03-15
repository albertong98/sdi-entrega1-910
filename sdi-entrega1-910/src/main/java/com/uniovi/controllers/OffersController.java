package com.uniovi.controllers;

import java.security.Principal;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.services.OffersService;
import com.uniovi.services.UsersService;

@Controller
public class OffersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private OffersService offersService;
	
	@RequestMapping("/offer/add")
	public String getOffer(Model model) {
		model.addAttribute("offer",new Offer());
		return "offer/add";
	}
	
	@RequestMapping(value = "/offer/add",method = RequestMethod.POST)
	public String setOffer(@ModelAttribute Offer offer,Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		offer.setSeller(user);
		offer.setDate(new Date(System.currentTimeMillis()));
		offersService.addOffer(offer);
		return "redirect:/home";
	}
}