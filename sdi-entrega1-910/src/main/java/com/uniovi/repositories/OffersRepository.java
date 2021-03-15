package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

public interface OffersRepository extends CrudRepository<Offer, Long> {
	@Query("SELECT o FROM Offer o WHERE o.seller = ?1 ORDER BY o.id ASC")
	public Page<Offer> findAllBySeller(Pageable pageable,User seller);
	
	@Query("SELECT o FROM Offer o WHERE (o.title LIKE %?1%)")
	Page<Offer> searchByTitle(Pageable pageable,String searchText);
	
	Page<Offer> findAll(Pageable pageable);
}
