package br.com.mcp.mcp_server.infrastructure.exception;

public class EmailJaExisteException extends RuntimeException {
    public EmailJaExisteException(String message) {
        super(message);
    }
}
