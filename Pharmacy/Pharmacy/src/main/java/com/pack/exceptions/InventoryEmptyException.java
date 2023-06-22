package com.pack.exceptions;

public class InventoryEmptyException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InventoryEmptyException(String msg)
	{
		super(msg);
	}

}
