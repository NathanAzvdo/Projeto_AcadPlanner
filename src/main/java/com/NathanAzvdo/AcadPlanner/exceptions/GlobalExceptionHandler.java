package com.NathanAzvdo.AcadPlanner.exceptions;

import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest; // Importar
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Método helper para criar a resposta
    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status, String message, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                Instant.now()
        );
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ErrorResponse> handleEmptyFieldException(
            EmptyFieldException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(FieldAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleFieldAlreadyExistsException(
            FieldAlreadyExistsException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<ErrorResponse> handleEmptyListException(
            EmptyListException ex, HttpServletRequest request) {
        // Se você mantiver essa exceção, 404 é melhor que 400
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIdException(
            InvalidIdException ex, HttpServletRequest request) {
        // MUDADO PARA 404
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException ex, HttpServletRequest request) {
        // MUDADO PARA 401
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessException(
            BusinessException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(FilterException.class)
    public ResponseEntity<ErrorResponse> filterException(
            FilterException ex, HttpServletRequest request) {
        // MUDADO PARA 401
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }
}