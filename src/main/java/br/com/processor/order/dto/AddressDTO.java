package br.com.processor.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private String postalCode;
    private String streetName;
    private String number;
    private String additionalInfo;
}
