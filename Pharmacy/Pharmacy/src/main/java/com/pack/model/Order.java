package com.pack.model;

import java.util.HashMap;

public class Order 
{
	private int id;
	private Customer customer;
	private HashMap<Medicine,Integer> cart;
	private double bill;
	
	private String deliveryDateAndTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public HashMap<Medicine, Integer> getCart() {
		return cart;
	}

	public void setCart(HashMap<Medicine, Integer> cart) {
		this.cart = cart;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
	}

	public String getDeliveryDateAndTime() {
		return deliveryDateAndTime;
	}

	public void setDeliveryDateAndTime(String deliveryDateAndTime) {
		this.deliveryDateAndTime = deliveryDateAndTime;
	}

	public Order(int id, Customer customer, HashMap<Medicine, Integer> cart, double bill, String deliveryDateAndTime) {
		super();
		this.id = id;
		this.customer = customer;
		this.cart = cart;
		this.bill = bill;
		this.deliveryDateAndTime = deliveryDateAndTime;
	}

	public Order() {
		super();
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", cart=" + cart + ", bill=" + bill
				+ ", deliveryDateAndTime=" + deliveryDateAndTime + "]";
	}
	
	
	
	
}
