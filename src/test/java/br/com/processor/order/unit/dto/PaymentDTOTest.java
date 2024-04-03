package br.com.processor.order.unit.dto;

import br.com.processor.order.enums.PaymentMethod;
import br.com.processor.order.enums.PaymentStatus;
import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentDTOTest {

    @Test
    public void shouldTestPaymentDTO() {
        var paymentDTO = ConstantsTests.getPaymentDTO();

        assertEquals(PaymentMethod.valueOf(ConstantsTests.PAYMENT_METHOD), paymentDTO.getMethod());
        assertEquals(ConstantsTests.PAYMENT_AMOUNT, paymentDTO.getAmount());
        assertEquals(PaymentStatus.valueOf(ConstantsTests.PAYMENT_STATUS), paymentDTO.getStatus());
    }
}
