package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.uniovi.entities.Offer;
import com.uniovi.repositories.OffersRepository;

public class OffersService {
	@Autowired
	private OffersRepository offersRepository;
	
	public void addOffer(Offer offer) {
		this.offersRepository.save(offer);
	}
}
