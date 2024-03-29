package com.uniovi.controllers;

import java.security.Principal;
import java.sql.Date;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@Autowired
	private MessageSource messageSource;
	
	Logger logger = LoggerFactory.getLogger(OffersController.class);
	
	@RequestMapping("/offer/add")
	public String getOffer(Model model) {
		logger.info("User uploading offer");
		model.addAttribute("offer",new Offer());
		return "offer/add";
	}
	
	@RequestMapping(value ="/offer/add",method = RequestMethod.POST)
	public String setOffer(@ModelAttribute @Validated Offer offer,Principal principal,BindingResult result) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		offer.setSeller(user);
		offer.setComprada(false);
		offer.setDate(new Date(System.currentTimeMillis()));
		
		offerAddValidator.validate(offer,result);
		
		if(result.hasErrors()) 
			return "offer/add";
	
		offersService.addOffer(offer);
		logger.info("Offer "+offer.getTitulo()+" uploaded succesfully by "+email);
		
		return "redirect:list";
	}
	
	@RequestMapping(value = "/offer/list")
	private String getList(Model model,Pageable pageable,Principal principal) {
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		
		String email = principal.getName();
		
		logger.info("User "+email+" accesing his offers list");
		
		Long id = usersService.getUserByEmail(email).getId();
		
		offers = offersService.getOffersBySeller(pageable,usersService.getUser(id));
		
		model.addAttribute("offersList",offers.getContent());
		model.addAttribute("page",offers);
		
		return "offer/list";
	}
	
	@RequestMapping(value = "/offer/delete/{id}")
	private String deleteOffer(@PathVariable Long id,Principal principal,RedirectAttributes redir) {
		User seller = this.usersService.getUserByEmail(principal.getName());
		Offer offer = this.offersService.getOffer(id);
		
		String errorMessage = "";
		
		if(seller.equals(offer.getSeller())){
			logger.info("User "+seller.getEmail()+" deleted his offer "+offer.getTitulo());
			this.offersService.deleteOffer(id);
		}else {
			errorMessage = messageSource.getMessage("Error.user.delete.other",null,null,LocaleContextHolder.getLocale());
			logger.error("User "+seller.getEmail()+" tried to delete an offer from other user");
		}
		
		redir.addFlashAttribute("errorMessage",errorMessage);
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
		
		logger.info("User searching for "+searchText);
		
		model.addAttribute("offerList",offerSearch.getContent());
		model.addAttribute("page",offerSearch);
		return "offer/search";
	}
	
	@RequestMapping(value="/offer/buy/{id}")
	private String buyOffer(@PathVariable Long id,Principal principal,RedirectAttributes redir) {
		Offer offer = offersService.getOffer(id);
		User buyer = usersService.getUserByEmail(principal.getName());
		
		String errorMessage = offersService.buyOffer(offer,buyer,messageSource);
		
		redir.addFlashAttribute("errorMessage",errorMessage);
		redir.addFlashAttribute("offer_id",offer.getId());
		
		if(!errorMessage.isEmpty()) {
			logger.error("User "+buyer.getEmail()+" tried to buy "+offer.getTitulo()+" but an error happended: "+errorMessage);
			return "redirect:/offer/search";
		}
		
		offersService.addOffer(offer);
		usersService.addUser(buyer);
		
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
		
		logger.info("User "+email+" accesing his orders list");
		
		return "offer/orders";
	}
}
