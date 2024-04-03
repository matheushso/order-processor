package br.com.processor.order.unit.dto;

import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressDTOTest {

    @Test
    public void shouldTestAddressDTO() {
        var addressDTO = ConstantsTests.getAddressDTO();

        assertEquals(ConstantsTests.POSTAL_CODE, addressDTO.getPostalCode());
        assertEquals(ConstantsTests.STREET_NAME, addressDTO.getStreetName());
        assertEquals(ConstantsTests.NUMBER, addressDTO.getNumber());
        assertEquals(ConstantsTests.ADDITIONAL_INFO, addressDTO.getAdditionalInfo());
    }
}
