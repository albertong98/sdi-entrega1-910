package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;
	@PostConstruct
	public void init() {
		User user1 = new User("pedro@email.com", "Pedro", "Díaz");
		user1.setPassword("123456");
		user1.setSaldo(100);
		user1.setRole(rolesService.getRoles()[0]);
		User user2 = new User("lucas@email.com", "Lucas", "Núñez");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);
		User user3 = new User("maria@email.com", "María", "Rodríguez");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		User user4 = new User("marta@email.com", "Marta", "Almonte");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("pelayo@email.com", "Pelayo", "Valdes");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);
		User admin = new User("admin@email.com", "Admin", "admin");
		admin.setPassword("admin");
		admin.setRole(rolesService.getRoles()[1]);
		
		Set<Offer> user1Offers = new HashSet<Offer>();
		user1Offers.add(new Offer(user1,"Coche","coche",10));
		user1.setOfertas(user1Offers);
		
		Set<Offer> user2Offers = new HashSet<Offer>();
		user2Offers.add(new Offer(user2,"Cama","cama",13));
		user2.setOfertas(user2Offers);
		
		Set<Offer> user3Offers = new HashSet<Offer>();
		user3Offers.add(new Offer(user3,"Consola","consola",5));
		user3.setOfertas(user3Offers);
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(admin);
	}
}
