package br.com.processor.order.enums;

import br.com.processor.order.exception.InvalidDataTypeException;

import java.util.Map;

public class EnumValidator {

    private static final Map<Class<?>, String> ERROR_MESSAGES = Map.of(
            OrderStatus.class, "O status do pedido '%s' é inválido.",
            PaymentMethod.class, "O método de pagamento '%s' é inválido.",
            PaymentStatus.class, "O status do pagamento '%s' é inválido."
    );

    public static <E extends Enum<E>> void validateEnum(Class<E> enumClass, String enumValue) {
        if (enumValue != null) {
            try {
                enumValue = enumValue.toUpperCase();
                Enum.valueOf(enumClass, enumValue);
            } catch (IllegalArgumentException ex) {
                String baseErrorMessage = ERROR_MESSAGES.get(enumClass);
                String errorMessage = String.format(baseErrorMessage, enumValue);
                throw new InvalidDataTypeException(errorMessage);
            }
        }
    }
}
