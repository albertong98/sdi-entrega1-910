package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	
	private String name;
	private String lastName;
	
	private String password;
	@Transient 
	private String passwordConfirm;
	
	private double saldo;
	
	private String role;
	
	@OneToMany(mappedBy = "seller",cascade= CascadeType.ALL)
	private Set<Offer> ofertas;
	@OneToMany(mappedBy = "buyer",cascade= CascadeType.ALL)
	private Set<Offer> compras;
	@OneToMany(mappedBy = "user",cascade= CascadeType.ALL)
	private Set<Conversation> conversaciones;
	
	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public User() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Offer> getOfertas() {
		return ofertas;
	}

	public void setOfertas(Set<Offer> ofertas) {
		this.ofertas = ofertas;
	}

	public Set<Offer> getCompras() {
		return compras;
	}

	public void setCompras(Set<Offer> compras) {
		this.compras = compras;
	}
}
