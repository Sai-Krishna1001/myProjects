package com.pack.model;

import java.util.HashMap;

public class Cart {
	
	HashMap<Integer,Medicine> customerCart = new HashMap<>();

	public Cart(HashMap<Integer,Medicine> customerCart) {
		super();
		this.customerCart = customerCart;
	}

	public HashMap<Integer,Medicine> getCustomerCart() {
		return customerCart;
	}

	public void setCustomerCart(HashMap<Integer,Medicine> customerCart) {
		this.customerCart = customerCart;
	}

	@Override
	public String toString() {
		return "Cart [customerCart=" + customerCart + "]";
	}

	public Cart() {
		super();
	}

	
	
	
}
