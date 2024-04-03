package br.com.processor.order.unit.advice;

import br.com.processor.order.advice.CostumControllerAdvice;
import br.com.processor.order.dto.ErrorDTO;
import br.com.processor.order.enums.ErrorType;
import br.com.processor.order.exception.AuthException;
import br.com.processor.order.exception.DateFormatException;
import br.com.processor.order.exception.InvalidDataTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CostumControllerAdviceTest {

    @InjectMocks
    private CostumControllerAdvice costumControllerAdvice;

    @Mock
    private MethodParameter methodParameter;

    @Test
    public void shouldValidateGetMissingRequestHeaderException() {
        when(methodParameter.getNestedParameterType()).thenReturn((Class) String.class);
        ResponseEntity<ErrorDTO> responseEntity = costumControllerAdvice.getMissingRequestHeaderException(new MissingRequestHeaderException("headerName", methodParameter));
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        assertEquals(ErrorType.NAO_AUTORIZADO.toString(), Objects.requireNonNull(responseEntity.getBody()).getErrorType());
    }

    @Test
    public void shouldValidateGetAuthException() {
        ResponseEntity<ErrorDTO> responseEntity = costumControllerAdvice.getAuthException(new AuthException());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        assertEquals(ErrorType.NAO_AUTORIZADO.toString(), Objects.requireNonNull(responseEntity.getBody()).getErrorType());
    }

    @Test
    public void shouldValidateGetJwtException() {
        ResponseEntity<ErrorDTO> responseEntity = costumControllerAdvice.getJwtException();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
        assertEquals(ErrorType.NAO_AUTORIZADO.toString(), Objects.requireNonNull(responseEntity.getBody()).getErrorType());
    }

    @Test
    public void shouldValidateGetDateFormatException() {
        ResponseEntity<ErrorDTO> responseEntity = costumControllerAdvice.getDateFormatException(new DateFormatException());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertEquals(ErrorType.DATA_INVALIDA.toString(), Objects.requireNonNull(responseEntity.getBody()).getErrorType());
    }

    @Test
    public void shouldValidateGetInvalidDataTypeException() {
        ResponseEntity<ErrorDTO> responseEntity = costumControllerAdvice.getInvalidDataTypeException(new InvalidDataTypeException("InvalidDataTypeException"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertEquals(ErrorType.ENTRADA_INVALIDA.toString(), Objects.requireNonNull(responseEntity.getBody()).getErrorType());
    }

    @Test
    public void shouldValidateGetException() {
        ResponseEntity<ErrorDTO> responseEntity = costumControllerAdvice.getException(new Exception());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertEquals(ErrorType.DESCONHECIDO.toString(), Objects.requireNonNull(responseEntity.getBody()).getErrorType());
    }
}
