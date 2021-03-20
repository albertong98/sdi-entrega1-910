package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Conversation;
import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repositories.ConversationsRepository;

@Service
public class ConversationsService {
	@Autowired
	private ConversationsRepository conversationsRepository;
	
	public List<Conversation> findConversationsForUser(User user){
		return this.conversationsRepository.findAllForUser(user);
	}
	
	public Conversation createConversation(User user, Offer offer) {
		Conversation c = new Conversation();
		c.setUser(user);
		c.setOffer(offer);
		this.addConversation(c);
		return c;
	}
	
	public void addConversation(Conversation c) {
		this.conversationsRepository.save(c);
	}
	
	public Conversation getConversation(Long id) {
		return this.conversationsRepository.findById(id).get();
	}
	
	public Conversation findConversationForUserAndOffer(User user,Offer offer) {
		return this.conversationsRepository.findConversationForUserAndOffer(user, offer);
	}
	
	public void deleteConversation(Long id) {
		this.conversationsRepository.deleteById(id);
	}
}
