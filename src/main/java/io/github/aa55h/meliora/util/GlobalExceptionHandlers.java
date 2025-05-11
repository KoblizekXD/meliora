package io.github.aa55h.meliora.util;

import io.github.aa55h.meliora.dto.ConstraintViolationResponse;
import io.github.aa55h.meliora.dto.GenericErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandlers {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GenericErrorResponse> handleAuthenticationException(HttpServletRequest request, AuthenticationException e) {
        GenericErrorResponse errorResponse = new GenericErrorResponse(
                e.getMessage(),
                request.getContextPath(),
                401,
                System.currentTimeMillis()
        );
        return ResponseEntity.status(401).body(errorResponse);
    }
    
    @ExceptionHandler(FileServiceException.class)
    public ResponseEntity<GenericErrorResponse> handleFileServiceException(HttpServletRequest request, FileServiceException e) {
        GenericErrorResponse errorResponse = new GenericErrorResponse(
                e.getMessage(),
                request.getContextPath(),
                500,
                System.currentTimeMillis()
        );
        return ResponseEntity.status(500).body(errorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ConstraintViolationResponse> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        var errorResponse = new ConstraintViolationResponse(
                "Validation failed for fields",
                request.getContextPath(),
                400,
                System.currentTimeMillis(),
                e.getBindingResult().getAllErrors().stream().map(it -> {
                    FieldError fe = (FieldError) it;
                    return new ConstraintViolationResponse.Property(fe.getField(), fe.getDefaultMessage());
                }).collect(Collectors.toSet()));
        return ResponseEntity.status(400).body(errorResponse);
    }
    
    @ExceptionHandler(UUIDParser.UUIDParseException.class)
    public ResponseEntity<GenericErrorResponse> handleUUIDParseException(HttpServletRequest request, UUIDParser.UUIDParseException e) {
        GenericErrorResponse errorResponse = new GenericErrorResponse(
                e.getMessage(),
                request.getContextPath(),
                400,
                System.currentTimeMillis()
        );
        return ResponseEntity.status(400).body(errorResponse);
    }
}
