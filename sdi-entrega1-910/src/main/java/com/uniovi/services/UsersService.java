package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@PostConstruct
	public void init() {
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		this.usersRepository.save(user);
	}
	
	public List<User> getUsers(){
		//TODO Paginacion?
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}
	
	public void deleteUser(User user) {
		this.usersRepository.delete(user);
	}
	
	public User getUser(Long id) {
		return this.usersRepository.findById(id).get();
	}
	
	public User getUserByEmail(String email) {
		return this.usersRepository.findByEmail(email);
	}
}
