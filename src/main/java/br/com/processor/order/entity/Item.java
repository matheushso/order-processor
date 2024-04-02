package br.com.processor.order.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    private String itemId;
    private String sku;
    private String name;
    private String description;
    private Double price;
    private String url;
}
