package com.pack.model;

public class Medicine 
{

	private int 	mid;
	private String  mname;
	private  int  	mprice;
	private int 	mquantity;
//	private Supplier supplier;
//	private PharmaCompany pcompany;
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public int getMprice() {
		return mprice;
	}
	public void setMprice(int mprice) {
		this.mprice = mprice;
	}
	public int getMquantity() {
		return mquantity;
	}
	public void setMquantity(int mquantity) {
		this.mquantity = mquantity;
	}
	public Medicine(int mid, String mname, int mprice, int mquantity) {
		super();
		this.mid = mid;
		this.mname = mname;
		this.mprice = mprice;
		this.mquantity = mquantity;
	}
	@Override
	public String toString() {
		return "Medicine [mid=" + mid + ", mname=" + mname + ", mprice=" + mprice + ", mquantity=" + mquantity + "]";
	}
	public Medicine() {
		super();
	}
	
	
	
	
	
}
