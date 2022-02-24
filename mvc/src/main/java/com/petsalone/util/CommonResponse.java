package com.petsalone.util;

import lombok.Data;

@Data
public class CommonResponse<T> {

	private T data;
	private int errorCode;
	private String message;
	private boolean success;
}
