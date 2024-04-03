package br.com.processor.order.unit.dto;

import br.com.processor.order.enums.OrderStatus;
import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderDTOTest {

    @Test
    public void shouldTestOrderDTO() {
        var orderDTO = ConstantsTests.getOrderDTO();

        assertEquals(ConstantsTests.ORDER_ID, orderDTO.getOrderId());
        assertEquals(ConstantsTests.CREATED_AT, orderDTO.getCreatedAt());
        assertEquals(ConstantsTests.UPDATED_AT, orderDTO.getUpdatedAt());
        assertEquals(OrderStatus.valueOf(ConstantsTests.STATUS), orderDTO.getStatus());
        assertEquals(1, orderDTO.getItems().size());
        assertEquals(ConstantsTests.SELLER_ID, orderDTO.getSeller());
        assertNotNull(orderDTO.getBuyer());
        assertNotNull(orderDTO.getShippingAddress());
        assertNotNull(orderDTO.getBillingAddress());
        assertNotNull(orderDTO.getPayment());
    }
}
