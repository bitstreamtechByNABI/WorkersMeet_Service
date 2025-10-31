package nabi.comworker.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import nabi.comworker.dto.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// ✅ Handle Custom Runtime Exceptions (e.g., email exists, etc.)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handleRuntime(RuntimeException ex, HttpServletRequest request) {
		return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
	}

	// ✅ Handle Validation Errors (@NotBlank, @Email, etc.)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getField() + ": " + e.getDefaultMessage()).findFirst().orElse("Validation failed");

		return buildErrorResponse(message, HttpStatus.BAD_REQUEST, request);
	}

	// ✅ Handle All Other Uncaught Exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest request) {
		return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	// 🔧 Utility: Build error from exception
	private ResponseEntity<ApiError> buildErrorResponse(Exception ex, HttpStatus status, HttpServletRequest request) {
		ApiError error = ApiError.builder().timestamp(LocalDateTime.now()).status(status.value())
				.error(status.getReasonPhrase()).message(ex.getMessage()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(error, status);
	}

	// 🔧 Utility: Build error from custom message
	private ResponseEntity<ApiError> buildErrorResponse(String message, HttpStatus status, HttpServletRequest request) {
		ApiError error = ApiError.builder().timestamp(LocalDateTime.now()).status(status.value())
				.error(status.getReasonPhrase()).message(message).path(request.getRequestURI()).build();
		return new ResponseEntity<>(error, status);
	}
	
	 
	
}
