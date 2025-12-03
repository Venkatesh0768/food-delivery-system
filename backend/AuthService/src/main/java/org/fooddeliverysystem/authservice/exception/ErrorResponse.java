package org.fooddeliverysystem.authservice.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String message,
        HttpStatus httpStatus,
        int statusCode
) {

}
