package com.petsalone.exception;

public class PetServiceException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PetServiceException(){
		super();
	}
	public PetServiceException(String message) {
		super(message);
	}
}
