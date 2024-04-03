package br.com.processor.order.unit.entity;

import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderTest {

    @Test
    public void shouldTestOrder() {
        var order = ConstantsTests.getOrder();

        assertEquals(ConstantsTests.ORDER_ID, order.getOrderId());
        assertEquals(ConstantsTests.CREATED_AT, order.getCreatedAt());
        assertEquals(ConstantsTests.UPDATED_AT, order.getUpdatedAt());
        assertEquals(ConstantsTests.STATUS, order.getStatus());
        assertEquals(1, order.getItems().size());
        assertEquals(ConstantsTests.SELLER_ID, order.getSeller());
        assertNotNull(order.getBuyer());
        assertNotNull(order.getShippingAddress());
        assertNotNull(order.getBillingAddress());
        assertNotNull(order.getPayment());
        assertNotNull(order.toString());
    }
}
