package br.com.processor.order.unit.dto;

import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemDTOTest {

    @Test
    public void shouldTestItemDTO() {
        var itemDTO = ConstantsTests.getItemDTO();

        assertEquals(ConstantsTests.ITEM_ID, itemDTO.getItemId());
        assertEquals(ConstantsTests.SKU, itemDTO.getSku());
        assertEquals(ConstantsTests.ITEM_NAME, itemDTO.getName());
        assertEquals(ConstantsTests.DESCRIPTION, itemDTO.getDescription());
        assertEquals(ConstantsTests.PRICE, itemDTO.getPrice());
        assertEquals(ConstantsTests.URL, itemDTO.getUrl());
    }
}
