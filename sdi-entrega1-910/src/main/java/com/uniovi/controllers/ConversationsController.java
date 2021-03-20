package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Conversation;
import com.uniovi.entities.Message;
import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.services.ConversationsService;
import com.uniovi.services.MessagesService;
import com.uniovi.services.OffersService;
import com.uniovi.services.UsersService;

@Controller
public class ConversationsController {
	@Autowired
	private ConversationsService conversationService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private OffersService offersService;
	@Autowired
	private MessagesService messagesService;
	
	@RequestMapping("/conversation/list")
	private String getList(Model model,Principal principal) {
		User user = this.usersService.getUserByEmail(principal.getName());
		
		List<Conversation> conversations = conversationService.findConversationsForUser(user);
		
		model.addAttribute("conversationList",conversations);
		
		return "conversation/list";
	}
	
	@RequestMapping("/conversation/{id}")
	private String getConversation(@PathVariable Long id,Model model,Principal principal) {
		Conversation c = conversationService.getConversation(id);
		List<Message> messages = this.messagesService.findMessagesForConversation(c);
		
		User user = this.usersService.getUserByEmail(principal.getName());
		
		if(!(c.getUser().equals(user) || c.getOffer().getSeller().equals(user)))
			return "redirect:/conversation/list";
		
		model.addAttribute("messagesList",messages);
		model.addAttribute("conversation",c);
		model.addAttribute("username",principal.getName());
		
		return "conversation/chat";
	}
	
	@RequestMapping("/conversation/create/{id}")
	private String createConversation(@PathVariable Long id,Principal principal) {
		User user = this.usersService.getUserByEmail(principal.getName());
		Offer offer = this.offersService.getOffer(id);
		
		Conversation c = this.conversationService.findConversationForUserAndOffer(user,offer);
		
		if(c == null)
			c = this.conversationService.createConversation(user,offer);
		
		return "redirect:/conversation/"+c.getId();
	}
	
	@RequestMapping(value = "/conversation/message/add",method = RequestMethod.POST)
	private String addMessage(Principal principal,@RequestParam(required=true) String content,@RequestParam Long id) {
		Conversation c = this.conversationService.getConversation(id);
		
		User user = this.usersService.getUserByEmail(principal.getName());
		
		if(!(c.getUser().equals(user) || c.getOffer().getSeller().equals(user)))
			return "redirect:/conversation/list";
		
		Message m = this.messagesService.createMessageForConversation(c,user,content);
		
		this.messagesService.addMessage(m);
		
		return "redirect:/conversation/"+c.getId();
	}
}
