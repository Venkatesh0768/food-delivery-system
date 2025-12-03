package org.fooddeliverysystem.authservice.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalException {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExist(EmailAlreadyExistException ex) {
        log.warn("Email conflict: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, 400));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, 404));
    }

    @ExceptionHandler({
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            CredentialsExpiredException.class,
            DisabledException.class,
            LockedException.class,
            ExpiredJwtException.class,
            JwtException.class,
            AuthenticationException.class
    })
    public ResponseEntity<ApiError> handleAuth(Exception ex, HttpServletRequest request) {

        HttpStatus status;

        if (ex instanceof DisabledException) {
            status = HttpStatus.FORBIDDEN; // 403
        } else if (ex instanceof LockedException) {
            status = HttpStatus.LOCKED; // 423
        } else if (ex instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED; // 401
        } else if (ex instanceof ExpiredJwtException || ex instanceof JwtException) {
            status = HttpStatus.UNAUTHORIZED; // 401
        } else if (ex instanceof AuthenticationException) {
            status = HttpStatus.UNAUTHORIZED; // 401
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        log.warn("Auth failure [{}]: {}", ex.getClass().getSimpleName(), ex.getMessage());

        ApiError body = ApiError.of(
                status,
                "Authentication error",
                safeMessage(ex),
                request.getRequestURI()
        );

        return ResponseEntity.status(status)
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .header("Pragma", "no-cache")
                .body(body);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String detail = buildValidationMessage(ex);

        ApiError body = ApiError.of(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                detail,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .header("Pragma", "no-cache")
                .body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknown(Exception ex, HttpServletRequest request) {
        log.error("Internal error: {}", ex.getMessage(), ex);

        ApiError body = ApiError.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                "Something went wrong",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .header("Pragma", "no-cache")
                .body(body);
    }

    private String buildValidationMessage(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(this::friendlyMessage)
                .collect(Collectors.joining(", "));
    }

    private String friendlyMessage(FieldError fe) {
        return fe.getDefaultMessage() != null ? fe.getDefaultMessage() : fe.getField() + " is invalid";
    }

    private String safeMessage(Exception ex) {
        String msg = ex.getMessage();
        return (msg == null || msg.isBlank()) ? "Invalid credentials or expired token" : msg;
    }

    public record ApiError(
            OffsetDateTime timestamp,
            int status,
            String error,
            String message,
            String path
    ) {
        public static ApiError of(HttpStatus status, String error, String message, String path) {
            return new ApiError(OffsetDateTime.now(ZoneOffset.UTC), status.value(), error, message, path);
        }
    }
}
