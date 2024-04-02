package br.com.processor.order.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@CompoundIndex(name = "status_createdAt_index", def = "{'status': 1, 'createdAt': -1}")
@Document(collection = "orders")
@Getter
@Setter
@ToString
public class Order {

    @Id
    private String orderId;
    private Long createdAt;
    private Long updatedAt;
    private String status;
    private List<Item> items;
    private String seller;
    private Buyer buyer;
    private Address shippingAddress;
    private Address billingAddress;
    private Payment payment;
}
