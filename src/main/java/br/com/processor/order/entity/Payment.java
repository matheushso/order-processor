package br.com.processor.order.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {

    private String method;
    private Double amount;
    private String status;
}
