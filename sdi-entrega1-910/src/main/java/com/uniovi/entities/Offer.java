package com.uniovi.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Offer {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="seller_id")
	private User seller;
	
	@ManyToOne
	@JoinColumn(name="buyer_id")
	private User buyer;
	
	private String titulo;
	private boolean comprada;
	private String descripcion;
	private double precio;
	private Date date;
	
	public Offer() {
		this.setComprada(false);
		this.setDate(new Date(System.currentTimeMillis()));
	}
	
	public Offer(User user) {
		super();
		this.setSeller(user);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public boolean isComprada() {
		return comprada;
	}
	public void setComprada(boolean comprada) {
		this.comprada = comprada;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
