package com.userregistration.application.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.userregistration.application.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("VALIDATION_FAILED");
		List<String> details = ex.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		ErrorResponse errors = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				"VALIDATION_FAILED", details);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(UserNameAlreadyExistsException.class)
	public final ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(UserNameAlreadyExistsException ex,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse errors = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				"User already Exists", details);
		log.error(errors.toString());
		return new ResponseEntity<ErrorResponse>(errors, HttpStatus.CONFLICT);

	}
	
}
