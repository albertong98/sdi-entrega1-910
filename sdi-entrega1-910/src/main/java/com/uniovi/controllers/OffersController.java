package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		offersService.addOffer(offer);
		return "redirect:/list";
	}
	
	@RequestMapping(value = "/offer/list")
	private String getList(Model model,Pageable pageable,Principal principal) {
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		
		String email = principal.getName();
		Long id = usersService.getUserByEmail(email).getId();
		
		offers = offersService.getOffersBySeller(pageable,usersService.getUser(id));
		
		model.addAttribute("offersList",offers);
		model.addAttribute("page",offers);
		
		return "offer/list";
	}
	
	@RequestMapping(value = "/offer/delete/{id}")
	private String deleteOffer(@PathVariable Long id) {
		this.offersService.deleteOffer(id);
		return "redirect:offer/list";
	}
}
