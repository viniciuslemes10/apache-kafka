package br.com.microservices.orchestrated.orderservice.config.exceptions;

public record ExceptionDetails(
        int status,
        String message
) {
}
