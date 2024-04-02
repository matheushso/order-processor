package br.com.processor.order.dto;

import br.com.processor.order.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private String orderId;
    private Long createdAt;
    private Long updatedAt;
    private OrderStatus status;
    private List<ItemDTO> items;
    private String seller;
    private BuyerDTO buyer;
    private AddressDTO shippingAddress;
    private AddressDTO billingAddress;
    private PaymentDTO payment;
}
