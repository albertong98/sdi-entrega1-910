package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repositories.OffersRepository;

public class OffersService {
	@Autowired
	private OffersRepository offersRepository;
	
	public void addOffer(Offer offer) {
		this.offersRepository.save(offer);
	}
	
	public Page<Offer> getOffersBySeller(Pageable pageable,User user){
		return this.offersRepository.findAllBySeller(pageable, user);
	}
}
