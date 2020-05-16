package com.viveksoft.dataanalysis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.viveksoft.dataanalysis.exception.ServiceException;

@ControllerAdvice
public class ExceptionMapper extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String getResponseForServiceException(ServiceException e) {
		return e.getMessage();
	}

}
