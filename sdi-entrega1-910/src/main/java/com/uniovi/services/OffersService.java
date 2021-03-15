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
	
	public Offer getOffer(Long id) {
		return this.offersRepository.findById(id).get();
	}
	
	public void deleteOffer(Long id) {
		this.offersRepository.deleteById(id);
	} 
	
	public Page<Offer> searchOffersByTitle(Pageable pageable,String searchText){
		return this.offersRepository.searchByTitle(pageable, searchText);
	}
	
	public Page<Offer> getOffers(Pageable pageable){
		return this.offersRepository.findAll(pageable);
	}
}
