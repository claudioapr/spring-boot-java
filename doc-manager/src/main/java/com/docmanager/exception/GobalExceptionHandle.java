package com.docmanager.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
/**
 * 
 * 
 * @author cresende
 *
 *this class handle specific exception as well as global ones
 */
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
@ControllerAdvice
public class GobalExceptionHandle {
	
	//specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	 @ExceptionHandler(Exception.class)
	 public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
	  ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
}
