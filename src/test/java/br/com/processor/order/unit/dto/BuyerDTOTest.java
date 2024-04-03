package br.com.processor.order.unit.dto;

import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyerDTOTest {

    @Test
    public void shouldTestBuyerDTO() {
        var buyerDTO = ConstantsTests.getBuyerDTO();

        assertEquals(ConstantsTests.BUYER_ID, buyerDTO.getId());
        assertEquals(ConstantsTests.BUYER_NAME, buyerDTO.getName());
        assertEquals(ConstantsTests.BUYER_EMAIL, buyerDTO.getEmail());
    }
}
