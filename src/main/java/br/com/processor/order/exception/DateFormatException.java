package br.com.processor.order.exception;

public class DateFormatException extends RuntimeException {

    private static final String DATA_INVALIDA = "A data informada é inválida.";

    public DateFormatException() {
        super(DATA_INVALIDA);
    }

    public DateFormatException(String message) {
        super(message);
    }
}
