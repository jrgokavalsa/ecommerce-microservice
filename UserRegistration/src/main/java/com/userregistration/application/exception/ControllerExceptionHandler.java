package com.userregistration.application.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	// This exception will be raised when a handler method argument annotated with
	// @Valid failed validation
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		List<String> details = ex.getFieldErrors().stream().map(e1 -> e1.getField() + " : " + e1.getDefaultMessage())
				.collect(Collectors.toList());
		ErrorMessage message = new ErrorMessage(new Date(), "Validation Errors", request.getDescription(false),
				details);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	// This exception is thrown when a method parameter has the wrong type!
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "Invalid Arguments", request.getDescription(false),
				details);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	// This exception reports the result of Id not found
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "Resource Not Found ", request.getDescription(false),
				details);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InSufficientBalanceException.class)
	public ResponseEntity<ErrorMessage> inSufficientBalanceException(InSufficientBalanceException ex,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "Insufficient Balance :: ", request.getDescription(false),
				details);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CartDetailsNotFoundException.class)
	public ResponseEntity<ErrorMessage> cartDetailsNotFoundException(CartDetailsNotFoundException ex, WebRequest request)
	{
		List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
	    ErrorMessage message = new ErrorMessage(
	        new Date(),
	        "No Search Results :: ",
	        request.getDescription(false),details);
	    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	 }
	
	
	
	@ExceptionHandler(TransferLimitException.class)
	public ResponseEntity<ErrorMessage> transferLimitException(TransferLimitException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "Transfer Fund Excessed for account",
				request.getDescription(false), details);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BankOperationsException.class)
	public ResponseEntity<ErrorMessage> bankOperationsException(BankOperationsException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "Error during Account Opening Process",
				request.getDescription(false), details);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InSufficientFundException.class)
	public ResponseEntity<ErrorMessage> inSufficientFundException(InSufficientFundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), details);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNameAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> userNameAlreadyExistsException(UserNameAlreadyExistsException ex,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "User already exists", request.getDescription(false),
				details);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorMessage> searchResultsNotFoundException(InvalidCredentialsException ex,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "Invalid Credentials", request.getDescription(false),
				details);
		return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(OutOfStockException.class)
	public ResponseEntity<ErrorMessage> outOfStockException(OutOfStockException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "Out of Stock for the Product :: ",
				request.getDescription(false), details);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TransactionFailedException.class)
	public ResponseEntity<ErrorMessage> transactionFailedException(TransactionFailedException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "Transaction failed during processing! Please try Again",
				request.getDescription(false), details);
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FeignException.BadRequest.class)
	public Map<String, Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
		response.setStatus(e.status());
		return new JSONObject(e.contentUTF8()).toMap();
	}

	// This exception reports the result of all other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "Error occurred", request.getDescription(false), details);
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		List<String> details;
		details = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		ErrorMessage message = new ErrorMessage(new Date(), "Validation Failed", request.getDescription(false),
				details);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SearchResultsNotFoundException.class)
	public ResponseEntity<ErrorMessage> searchResultsNotFoundException(SearchResultsNotFoundException ex,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "No Search Results :: ", request.getDescription(false),
				details);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InavalidArgumentException.class)
	public ResponseEntity<ErrorMessage> inavalidArgumentException(InavalidArgumentException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorMessage message = new ErrorMessage(new Date(), "InValid Arugments  :: ", request.getDescription(false),
				details);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

}
