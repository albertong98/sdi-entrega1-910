package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.services.OffersService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.OfferAddValidator;

@Controller
public class OffersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private OffersService offersService;
	@Autowired
	private OfferAddValidator offerAddValidator;
	
	@RequestMapping("/offer/add")
	public String getOffer(Model model) {
		model.addAttribute("offer",new Offer());
		return "offer/add";
	}
	
	@RequestMapping(value = "/offer/add",method = RequestMethod.POST)
	public String setOffer(@ModelAttribute @Validated Offer offer,Principal principal,BindingResult result) {
		offerAddValidator.validate(offer,result);
		if(result.hasErrors()) return "/offer/add";
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
		
		model.addAttribute("offersList",offers.getContent());
		model.addAttribute("page",offers);
		
		return "offer/list";
	}
	
	@RequestMapping(value = "/offer/delete/{id}")
	private String deleteOffer(@PathVariable Long id,Principal principal) {
		User seller = this.usersService.getUserByEmail(principal.getName());
		Offer offer = this.offersService.getOffer(id);
		
		if(seller.equals(offer.getSeller()))
			this.offersService.deleteOffer(id);

		return "redirect:/offer/list";
	}
	
	@RequestMapping(value="/offer/search")
	private String searchOffers(Model model,Pageable pageable,Principal principal,@RequestParam(value="",required=false) String searchText) {
		Page<Offer> offerSearch = new PageImpl<Offer>(new LinkedList<Offer>());
		User user = this.usersService.getUserByEmail(principal.getName());
		
		if(searchText!=null && !searchText.isEmpty()) {
			searchText = "%"+searchText+"%";
			offerSearch = offersService.searchOffersByTitle(pageable,searchText,user);
		}
		else
			offerSearch = offersService.getOffers(pageable,user);
		
		model.addAttribute("offerList",offerSearch.getContent());
		model.addAttribute("page",offerSearch);
		return "offer/search";
	}
	
	@RequestMapping(value="/offer/buy/{id}")
	private String buyOffer(@PathVariable Long id,Principal principal) {
		Offer offer = offersService.getOffer(id);
		User buyer = usersService.getUserByEmail(principal.getName());

		if(offer.getPrecio() <= buyer.getSaldo()) {
			offer.setBuyer(buyer);
			offer.setComprada(true);
			
			buyer.setSaldo(buyer.getSaldo() - offer.getPrecio());
	
			offersService.addOffer(offer);
		}
		
		return "redirect:/offer/orders";
	}
	
	@RequestMapping(value = "/offer/orders")
	private String getOrders(Model model,Pageable pageable,Principal principal) {
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		
		String email = principal.getName();
		Long id = usersService.getUserByEmail(email).getId();
		
		offers = offersService.getOffersByBuyer(pageable,usersService.getUser(id));
		
		model.addAttribute("offerList",offers.getContent());
		model.addAttribute("page",offers);
		
		return "offer/orders";
	}
}
