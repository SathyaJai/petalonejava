package com.petsalone.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class JWTResponse  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
	public JWTResponse(String token) {
		this.token = token;
	}

}
