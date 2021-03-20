package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Conversation;
import com.uniovi.entities.Message;

public interface MessagesRepository extends CrudRepository<Message,Long>{
	@Query("SELECT m FROM Message m WHERE m.conversation = ?1")
	List<Message> findAllForConversation(Conversation c);
}
