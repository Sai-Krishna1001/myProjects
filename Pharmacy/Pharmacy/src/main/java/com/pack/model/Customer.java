package com.pack.model;

public class Customer {
	private int cid;
	private String cname;
	private long cnumber;
	private String address;
	
	
	private String username;
	private String password;
	private String role;
	
	private String membership;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public long getCnumber() {
		return cnumber;
	}

	public void setCnumber(long cnumber) {
		this.cnumber = cnumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public Customer(int cid, String cname, long cnumber, String address, String username, String password, String role,
			String membership) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.cnumber = cnumber;
		this.address = address;
		this.username = username;
		this.password = password;
		this.role = role;
		this.membership = membership;
	}

	public Customer() {
		super();
	}

	@Override
	public String toString() {
		return "Customer [cid=" + cid + ", cname=" + cname + ", cnumber=" + cnumber + ", address=" + address
				+ ", username=" + username + ", password=" + password + ", role=" + role + ", membership=" + membership
				+ "]";
	}
	
	
	
	
	
	
	
	
	

}
