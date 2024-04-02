package br.com.processor.order.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String postalCode;
    private String streetName;
    private String number;
    private String additionalInfo;
}
