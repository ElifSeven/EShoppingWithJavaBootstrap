package com.shoppingadmin.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ShippingAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String ShippingAddressName;
	private String ShippingAddressStreet1;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShippingAddressName() {
		return ShippingAddressName;
	}

	public void setShippingAddressName(String ShippingAddressName) {
		this.ShippingAddressName = ShippingAddressName;
	}

	public String getShippingAddressStreet1() {
		return ShippingAddressStreet1;
	}

	public void setShippingAddressStreet1(String ShippingAddressStreet1) {
		this.ShippingAddressStreet1 = ShippingAddressStreet1;
	}

	public Customer getUser() {
		return user;
	}

	public void setUser(Customer user) {
		this.user = user;
	}

}
