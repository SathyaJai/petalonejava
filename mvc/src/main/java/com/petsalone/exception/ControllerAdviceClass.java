package com.petsalone.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.petsalone.util.CommonResponse;

@ControllerAdvice
public class ControllerAdviceClass {

	
	@ExceptionHandler(PetServiceException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity< CommonResponse> perServiceException(final PetServiceException exception,
			final HttpServletRequest request) {

		CommonResponse error = new CommonResponse();
		error.setSuccess(false);
		error.setMessage(exception.getMessage());

		return  new ResponseEntity<>(error,HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity< CommonResponse> handleException(final Exception exception,
			final HttpServletRequest request) {

		CommonResponse error = new CommonResponse();
		error.setMessage(exception.getMessage());
		error.setSuccess(false);

		return  new ResponseEntity<>(error,HttpStatus.OK);
	}
}
