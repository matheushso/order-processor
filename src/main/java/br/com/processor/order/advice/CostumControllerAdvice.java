package br.com.processor.order.advice;

import br.com.processor.order.dto.ErrorDTO;
import br.com.processor.order.enums.ErrorType;
import br.com.processor.order.exception.AuthorizationException;
import br.com.processor.order.exception.DateFormatException;
import br.com.processor.order.exception.InvalidDataTypeException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CostumControllerAdvice {

    private static final String UNKNOWN_ERROR = "Erro inesperado. Entre em contato com o administrador do sistema!";
    private static final String UNAUTHORIZED = "Não informado o Authorization-JWT [Bearer Token], favor informar.";

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorDTO> getMissingRequestHeaderException(MissingRequestHeaderException ex) {
        return handleError(HttpStatus.UNAUTHORIZED, ErrorType.NAO_AUTORIZADO, ex, UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorDTO> getAuthorizationException(AuthorizationException ex) {
        return handleError(HttpStatus.UNAUTHORIZED, ErrorType.NAO_AUTORIZADO, ex, ex.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorDTO> getJwtException(JwtException ex) {
        return getAuthorizationException(new AuthorizationException());
    }

    @ExceptionHandler(DateFormatException.class)
    public ResponseEntity<ErrorDTO> getDateFormatException(DateFormatException ex) {
        return handleError(HttpStatus.BAD_REQUEST, ErrorType.DATA_INVALIDA, ex, ex.getMessage());
    }

    @ExceptionHandler(InvalidDataTypeException.class)
    public ResponseEntity<ErrorDTO> getInvalidDataTypeException(InvalidDataTypeException ex) {
        return handleError(HttpStatus.BAD_REQUEST, ErrorType.ENTRADA_INVALIDA, ex, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> getException(Exception ex) {
        return handleError(HttpStatus.BAD_REQUEST, ErrorType.DESCONHECIDO, ex, UNKNOWN_ERROR);
    }

    private ResponseEntity<ErrorDTO> handleError(HttpStatus httpStatus, ErrorType errorType, Throwable throwable, String message) {
        log.error(" " + throwable);
        return ResponseEntity.status(httpStatus).body(new ErrorDTO(errorType.toString(), message));
    }
}
