package br.com.processor.order.exception;

public class AuthorizationException extends RuntimeException {

    private static final String AUTORIZACAO_INVALIDA = "O token JWT informado é invalido.";

    public AuthorizationException() {
        super(AUTORIZACAO_INVALIDA);
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
