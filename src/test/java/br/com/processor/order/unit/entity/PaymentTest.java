package br.com.processor.order.unit.entity;

import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    @Test
    public void shouldTestPayment() {
        var payment = ConstantsTests.getPayment();

        assertEquals(ConstantsTests.PAYMENT_METHOD, payment.getMethod());
        assertEquals(ConstantsTests.PAYMENT_AMOUNT, payment.getAmount());
        assertEquals(ConstantsTests.PAYMENT_STATUS, payment.getStatus());
    }
}
