package br.com.processor.order.unit.enums;

import br.com.processor.order.enums.EnumValidator;
import br.com.processor.order.enums.OrderStatus;
import br.com.processor.order.enums.PaymentMethod;
import br.com.processor.order.enums.PaymentStatus;
import br.com.processor.order.exception.InvalidDataTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnumValidatorTest {

    private static final String ENUM_INVALID = "NOT_VALID";

    @Test
    public void validateEnumWhenEnumValueNullReturnWithoutDoingAnything() {
        EnumValidator.validateEnum(OrderStatus.class, null);
    }

    @Test
    public void validateEnumWhenOrderStatusValidNotReturnException() {
        EnumValidator.validateEnum(OrderStatus.class, OrderStatus.FINISHED.toString());
    }

    @Test
    public void validateEnumWhenPaymentMethodValidNotReturnException() {
        EnumValidator.validateEnum(PaymentMethod.class, PaymentMethod.GIFT_CARD.toString());
    }

    @Test
    public void validateEnumWhenPaymentStatusValidNotReturnException() {
        EnumValidator.validateEnum(PaymentStatus.class, PaymentStatus.PENDING.toString());
    }

    @Test
    public void validateEnumWhenOrderStatusInvalidReturnInvalidDataTypeException() {
        InvalidDataTypeException ex = assertThrows(InvalidDataTypeException.class, () -> EnumValidator.validateEnum(OrderStatus.class, ENUM_INVALID));

        assertEquals(String.format("O status do pedido '%s' é inválido.", ENUM_INVALID), ex.getMessage());
    }

    @Test
    public void validateEnumWhenPaymentMethodInvalidReturnInvalidDataTypeException() {
        InvalidDataTypeException ex = assertThrows(InvalidDataTypeException.class, () -> EnumValidator.validateEnum(PaymentMethod.class, ENUM_INVALID));

        assertEquals(String.format("O método de pagamento '%s' é inválido.", ENUM_INVALID), ex.getMessage());
    }

    @Test
    public void validateEnumWhenPaymentStatusInvalidReturnInvalidDataTypeException() {
        InvalidDataTypeException ex = assertThrows(InvalidDataTypeException.class, () -> EnumValidator.validateEnum(PaymentStatus.class, ENUM_INVALID));

        assertEquals(String.format("O status do pagamento '%s' é inválido.", ENUM_INVALID), ex.getMessage());
    }
}
