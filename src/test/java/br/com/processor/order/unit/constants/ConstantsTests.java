package br.com.processor.order.unit.constants;

import br.com.processor.order.dto.*;
import br.com.processor.order.entity.*;
import br.com.processor.order.enums.OrderStatus;
import br.com.processor.order.enums.PaymentMethod;
import br.com.processor.order.enums.PaymentStatus;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ConstantsTests {

    public static final String SELLER_ID = "660810848a754e0c32d6ca2a";
    public static final String ORDER_ID = "660b6bb981464e1e2e1929f1";
    public static final long CREATED_AT = 1712013676366L;
    public static final long UPDATED_AT = 1711840924574L;
    public static final String STATUS = OrderStatus.APPROVED.toString();
    public static final String ITEM_ID = "ABC0123456789";
    public static final String SKU = "SKU_DO_SELLER_123";
    public static final String ITEM_NAME = "Desempenador de pipa";
    public static final String DESCRIPTION = "O melhor desempenador de pipa que você verá na sua vida. Tração 4x4 e efeitos sonoros ideais para o seu churrasco";
    public static final double PRICE = 1499.99;
    public static final String URL = "https://www.minha-lojinha.com.br/products/ABC0123456789";
    public static final String BUYER_ID = "BCD2F2CCBA";
    public static final String BUYER_NAME = "Pietro Alcantara";
    public static final String BUYER_EMAIL = "p2alcantara_teste@gmail.com";
    public static final String POSTAL_CODE = "04540-010";
    public static final String STREET_NAME = "Rua do bobos";
    public static final String NUMBER = "456 A";
    public static final String ADDITIONAL_INFO = "Apto 37";
    public static final String PAYMENT_METHOD = PaymentMethod.DEBIT.toString();
    public static final double PAYMENT_AMOUNT = 1499.99;
    public static final String PAYMENT_STATUS = PaymentStatus.REFUSED.toString();

    public static Order getOrder() {
        var order = new Order();
        order.setOrderId(ORDER_ID);
        order.setCreatedAt(CREATED_AT);
        order.setUpdatedAt(UPDATED_AT);
        order.setStatus(STATUS);
        order.setItems(List.of(getItem()));
        order.setSeller(SELLER_ID);
        order.setBuyer(getBuyer());
        order.setShippingAddress(getAddress());
        order.setBillingAddress(getAddress());
        order.setPayment(getPayment());
        return order;
    }

    public static Item getItem() {
        var item = new Item();
        item.setItemId(ITEM_ID);
        item.setSku(SKU);
        item.setName(ITEM_NAME);
        item.setDescription(DESCRIPTION);
        item.setPrice(PRICE);
        item.setUrl(URL);
        return item;
    }

    public static Buyer getBuyer() {
        var buyer = new Buyer();
        buyer.setId(BUYER_ID);
        buyer.setName(BUYER_NAME);
        buyer.setEmail(BUYER_EMAIL);
        return buyer;
    }

    public static Address getAddress() {
        var address = new Address();
        address.setPostalCode(POSTAL_CODE);
        address.setStreetName(STREET_NAME);
        address.setNumber(NUMBER);
        address.setAdditionalInfo(ADDITIONAL_INFO);
        return address;
    }

    public static Payment getPayment() {
        var payment = new Payment();
        payment.setMethod(PAYMENT_METHOD);
        payment.setAmount(PAYMENT_AMOUNT);
        payment.setStatus(PAYMENT_STATUS);
        return payment;
    }

    public static OrderDTO getOrderDTO() {
        var orderDTO = new OrderDTO();
        orderDTO.setOrderId(ORDER_ID);
        orderDTO.setCreatedAt(CREATED_AT);
        orderDTO.setUpdatedAt(UPDATED_AT);
        orderDTO.setStatus(OrderStatus.valueOf(STATUS));
        orderDTO.setItems(List.of(getItemDTO()));
        orderDTO.setSeller(SELLER_ID);
        orderDTO.setBuyer(getBuyerDTO());
        orderDTO.setShippingAddress(getAddressDTO());
        orderDTO.setBillingAddress(getAddressDTO());
        orderDTO.setPayment(getPaymentDTO());
        return orderDTO;
    }

    public static ItemDTO getItemDTO() {
        var itemDTO = new ItemDTO();
        itemDTO.setItemId(ITEM_ID);
        itemDTO.setSku(SKU);
        itemDTO.setName(ITEM_NAME);
        itemDTO.setDescription(DESCRIPTION);
        itemDTO.setPrice(PRICE);
        itemDTO.setUrl(URL);
        return itemDTO;
    }

    public static BuyerDTO getBuyerDTO() {
        var buyerDTO = new BuyerDTO();
        buyerDTO.setId(BUYER_ID);
        buyerDTO.setName(BUYER_NAME);
        buyerDTO.setEmail(BUYER_EMAIL);
        return buyerDTO;
    }

    public static AddressDTO getAddressDTO() {
        var addressDTO = new AddressDTO();
        addressDTO.setPostalCode(POSTAL_CODE);
        addressDTO.setStreetName(STREET_NAME);
        addressDTO.setNumber(NUMBER);
        addressDTO.setAdditionalInfo(ADDITIONAL_INFO);
        return addressDTO;
    }

    public static PaymentDTO getPaymentDTO() {
        var paymentDTO = new PaymentDTO();
        paymentDTO.setMethod(PaymentMethod.valueOf(PAYMENT_METHOD));
        paymentDTO.setAmount(PAYMENT_AMOUNT);
        paymentDTO.setStatus(PaymentStatus.valueOf(PAYMENT_STATUS));
        return paymentDTO;
    }
}
