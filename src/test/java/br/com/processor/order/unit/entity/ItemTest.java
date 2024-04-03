package br.com.processor.order.unit.entity;

import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    @Test
    public void shouldTestItem() {
        var item = ConstantsTests.getItem();

        assertEquals(ConstantsTests.ITEM_ID, item.getItemId());
        assertEquals(ConstantsTests.SKU, item.getSku());
        assertEquals(ConstantsTests.ITEM_NAME, item.getName());
        assertEquals(ConstantsTests.DESCRIPTION, item.getDescription());
        assertEquals(ConstantsTests.PRICE, item.getPrice());
        assertEquals(ConstantsTests.URL, item.getUrl());
    }
}
