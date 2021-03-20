package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Conversation;
import com.uniovi.entities.Message;
import com.uniovi.entities.User;
import com.uniovi.repositories.MessagesRepository;

@Service
public class MessagesService {
	@Autowired
	private MessagesRepository messagesRepository;
	
	public List<Message> findMessagesForConversation(Conversation c){
		return this.messagesRepository.findAllForConversation(c);
	}
	
	public Message createMessageForConversation(Conversation c,User author,String content) {
		Message m = new Message();
		m.setSender(author.getEmail());
		m.setConversation(c);
		m.setContent(content);
		return m;
	}
	
	public void addMessage(Message m) {
		this.messagesRepository.save(m);
	}
}
