package br.com.microservices.orchestrated.orchestratorservice.config.exceptions;

public record ExceptionDetails(
        int status,
        String message
) {
}
