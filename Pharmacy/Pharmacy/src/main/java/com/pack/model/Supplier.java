package com.pack.model;

public class Supplier {
	private int sid;
	private String sname;
	private int scontact;
	private String address;
	public Supplier(int sid, String sname, int scontact, String address) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.scontact = scontact;
		this.address = address;
	}
	public Supplier() {
		super();
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getScontact() {
		return scontact;
	}
	public void setScontact(int scontact) {
		this.scontact = scontact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Supplier [sid=" + sid + ", sname=" + sname + ", scontact=" + scontact + ", address=" + address + "]";
	}
	
	
	
	

}
