package com.pack.model;

import java.util.HashMap;


public class PharmaCompany 
{
  
	private int pcid;
	private String pcname;
	private int pcontact;
	private String pcadress;
	private String email;
	private String password;
	private String role;
	
	private HashMap<Integer,Medicine> inventoryMap;

	public int getPcid() {
		return pcid;
	}

	public void setPcid(int pcid) {
		this.pcid = pcid;
	}

	public String getPcname() {
		return pcname;
	}

	public void setPcname(String pcname) {
		this.pcname = pcname;
	}

	public int getPcontact() {
		return pcontact;
	}

	public void setPcontact(int pcontact) {
		this.pcontact = pcontact;
	}

	public String getPcadress() {
		return pcadress;
	}

	public void setPcadress(String pcadress) {
		this.pcadress = pcadress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public HashMap<Integer, Medicine> getInventoryMap() {
		return inventoryMap;
	}

	public void setInventoryMap(HashMap<Integer, Medicine> inventoryMap) {
		this.inventoryMap = inventoryMap;
	}

	public PharmaCompany(int pcid, String pcname, int pcontact, String pcadress, String email, String password,
			String role, HashMap<Integer, Medicine> inventoryMap) {
		super();
		this.pcid = pcid;
		this.pcname = pcname;
		this.pcontact = pcontact;
		this.pcadress = pcadress;
		this.email = email;
		this.password = password;
		this.role = role;
		this.inventoryMap = inventoryMap;
	}

	@Override
	public String toString() {
		return "PharmaCompany [pcid=" + pcid + ", pcname=" + pcname + ", pcontact=" + pcontact + ", pcadress="
				+ pcadress + ", email=" + email + ", password=" + password + ", role=" + role + ", inventoryMap="
				+ inventoryMap + "]";
	}

	public PharmaCompany() {
		super();
	}
	
	
	
	
	
	
	 
	
  
  
}
