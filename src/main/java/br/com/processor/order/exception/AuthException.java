package br.com.processor.order.exception;

public class AuthException extends RuntimeException {

    private static final String AUTORIZACAO_INVALIDA = "O token JWT informado é invalido.";

    public AuthException() {
        super(AUTORIZACAO_INVALIDA);
    }

    public AuthException(String message) {
        super(message);
    }
}
