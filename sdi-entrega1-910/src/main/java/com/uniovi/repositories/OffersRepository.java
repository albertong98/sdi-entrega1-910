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
	
	@Query("SELECT o FROM Offer o WHERE LOWER(o.titulo) LIKE LOWER(?1) AND o.seller <> ?2")
	Page<Offer> searchByTitle(Pageable pageable,String searchText,User user);
	
	@Query("SELECT o FROM Offer o WHERE o.seller <> ?1")
	Page<Offer> findAllForUser(Pageable pageable,User user);
	
	@Query("SELECT o FROM Offer o WHERE o.buyer = ?1 ORDER BY o.id ASC")
	public Page<Offer> findAllByBuyer(Pageable pageable,User buyer);
}
