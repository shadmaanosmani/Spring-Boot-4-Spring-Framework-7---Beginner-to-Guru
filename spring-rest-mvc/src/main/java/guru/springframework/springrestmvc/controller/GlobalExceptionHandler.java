package guru.springframework.springrestmvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import guru.springframework.springrestmvc.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<List<Map<String, String>>> handleTransactionSystemException(TransactionSystemException ex) {
		
		ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

		if (ex.getCause().getCause() instanceof ConstraintViolationException cex) {
			
			var errorList = cex.getConstraintViolations()
							   .stream()
							   .map(constraintViolation -> Map.of(constraintViolation.getPropertyPath().toString(),
									constraintViolation.getMessage()))
							   .toList();
			
			return responseEntity.body(errorList);
			
		}
		
		return responseEntity.build();

	}


	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Void> handleNotFoundException() {

		return ResponseEntity.notFound().build();

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<Map<String, String>>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {

		var errorList = ex.getBindingResult()
						  .getFieldErrors()
						  .stream()
						  .map(fieldError -> Map.of(fieldError.getField(), fieldError.getDefaultMessage()))
						  .toList();

		return ResponseEntity.badRequest().body(errorList);

	}

}
