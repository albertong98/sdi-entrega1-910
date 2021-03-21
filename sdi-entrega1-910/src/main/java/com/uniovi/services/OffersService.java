package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repositories.OffersRepository;

@Service
public class OffersService {
	@Autowired
	private OffersRepository offersRepository;

	@PostConstruct
	public void init() {
	}
	
	public void addOffer(Offer offer) {
		this.offersRepository.save(offer);
	}
	
	public Page<Offer> getOffersBySeller(Pageable pageable,User user){
		return this.offersRepository.findAllBySeller(pageable, user);
	}
	
	public Page<Offer> getOffersByBuyer(Pageable pageable,User user){
		return this.offersRepository.findAllByBuyer(pageable, user);
	}
	
	public Offer getOffer(Long id) {
		return this.offersRepository.findById(id).get();
	}
	
	public void deleteOffer(Long id) {
		this.offersRepository.deleteById(id);
	} 
	
	public Page<Offer> searchOffersByTitle(Pageable pageable,String searchText,User user){
		return this.offersRepository.searchByTitle(pageable, searchText,user);
	}
	
	public Page<Offer> getOffers(Pageable pageable,User user){
		return this.offersRepository.findAllForUser(pageable,user);
	}
	
	public String buyOffer(Offer offer,User buyer,MessageSource messageSource) {	
		if(offer.isComprada())
			return messageSource.getMessage("Error.user.sold",null,null,LocaleContextHolder.getLocale());
		if(offer.getPrecio() > buyer.getSaldo())
			return messageSource.getMessage("Error.user.balance",null,null,LocaleContextHolder.getLocale());
		
		offer.setBuyer(buyer);
		offer.setComprada(true);
		buyer.setSaldo(buyer.getSaldo() - offer.getPrecio());
		
		return "";
	}
}
