package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Conversation;
import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

public interface ConversationsRepository extends CrudRepository<Conversation, Long> {
	
	@Query("SELECT c FROM Conversation c where c.user = ?1 OR c.offer.seller = ?1")
	List<Conversation> findAllForUser(User user);
	
	@Query("SELECT c FROM Conversation c WHERE c.user = ?1 AND c.offer = ?2")
	Conversation findConversationForUserAndOffer(User user,Offer offer);
}
