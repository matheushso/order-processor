package br.com.processor.order.unit.entity;

import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyerTest {

    @Test
    public void shouldTestBuyer() {
        var buyer = ConstantsTests.getBuyer();

        assertEquals(ConstantsTests.BUYER_ID, buyer.getId());
        assertEquals(ConstantsTests.BUYER_NAME, buyer.getName());
        assertEquals(ConstantsTests.BUYER_EMAIL, buyer.getEmail());
    }
}
