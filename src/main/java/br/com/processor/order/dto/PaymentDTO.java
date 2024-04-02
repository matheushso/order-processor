package br.com.processor.order.dto;

import br.com.processor.order.enums.PaymentMethod;
import br.com.processor.order.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

    private PaymentMethod method;
    private Double amount;
    private PaymentStatus status;
}
