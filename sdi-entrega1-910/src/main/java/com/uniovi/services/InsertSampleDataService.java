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
		this.initdb();
	}
	
	public void initdb() {
		this.usersService.deleteAll();
		
		User user1 = new User("pedro@email.com", "Pedro", "Díaz");
		user1.setPassword("123456");
		user1.setSaldo(100);
		user1.setRole(rolesService.getRoles()[0]);
		User user2 = new User("lucas@email.com", "Lucas", "Núñez");
		user2.setPassword("123456");
		user2.setSaldo(100);
		user2.setRole(rolesService.getRoles()[0]);
		User user3 = new User("maria@email.com", "María", "Rodríguez");
		user3.setPassword("123456");
		user3.setSaldo(100);
		user3.setRole(rolesService.getRoles()[0]);
		User user4 = new User("marta@email.com", "Marta", "Almonte");
		user4.setPassword("123456");
		user4.setSaldo(100);
		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("pelayo@email.com", "Pelayo", "Valdes");
		user5.setPassword("123456");
		user5.setSaldo(100);
		user5.setRole(rolesService.getRoles()[0]);
		User user6 = new User("martin@email.com", "Martin", "Garcia");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[0]);
		user6.setSaldo(100);
		User admin = new User("admin@email.com", "Admin", "admin");
		admin.setPassword("admin");
		admin.setSaldo(0);
		admin.setRole(rolesService.getRoles()[1]);
		
		Set<Offer> user1Offers = new HashSet<Offer>();
		user1Offers.add(new Offer(user1,"Coche","coche",10));
		user1.setOfertas(user1Offers);
		
		Set<Offer> user2Offers = new HashSet<Offer>();
		user2Offers.add(new Offer(user2,"Cama","cama",13));
		user2Offers.add(new Offer(user2,"Joya","joya",100));
		user2.setOfertas(user2Offers);
		
		Set<Offer> user3Offers = new HashSet<Offer>();
		user3Offers.add(new Offer(user3,"Consola","consola",5));
		user3Offers.add(new Offer(user3,"Avion","avion",110));
		user3.setOfertas(user3Offers);
		
		Set<Offer> user5Offers = new HashSet<Offer>();
		user5Offers.add(new Offer(user5,"Zapatos","zapatos",5));
		user5Offers.add(new Offer(user5,"Vaqueros","vaqueros",3));
		user5Offers.add(new Offer(user5,"Chaqueta","chaqueta",5));
		user5.setOfertas(user5Offers);
		Set<Offer> user5Orders = new HashSet<Offer>();
		user5Orders.add(new Offer(user2,"Refresco","refresco",5));
		user5Orders.add(new Offer(user2,"Mando","mando",3));
		user5.setCompras(user5Orders);
		
		Set<Offer> user6Offers = new HashSet<Offer>();
		user6Offers.add(new Offer(user6,"Coche","coche",10));
		user6.setOfertas(user6Offers);
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(admin);
	}
}
