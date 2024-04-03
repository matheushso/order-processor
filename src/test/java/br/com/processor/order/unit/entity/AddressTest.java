package br.com.processor.order.unit.entity;

import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {

    @Test
    public void shouldTestAddress() {
        var address = ConstantsTests.getAddress();

        assertEquals(ConstantsTests.POSTAL_CODE, address.getPostalCode());
        assertEquals(ConstantsTests.STREET_NAME, address.getStreetName());
        assertEquals(ConstantsTests.NUMBER, address.getNumber());
        assertEquals(ConstantsTests.ADDITIONAL_INFO, address.getAdditionalInfo());
    }
}
