package guru.springframework.springrestmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import guru.springframework.springrestmvc.exception.NotFoundException;

@RestControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Void> handleNotFoundException() {
		
		return ResponseEntity.notFound().build();
		
	}

}
