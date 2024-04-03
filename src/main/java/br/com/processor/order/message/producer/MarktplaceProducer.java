package br.com.processor.order.message.producer;

import br.com.processor.order.dto.*;
import br.com.processor.order.enums.OrderStatus;
import br.com.processor.order.enums.PaymentMethod;
import br.com.processor.order.enums.PaymentStatus;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@EnableScheduling
public class MarktplaceProducer {

    @Autowired
    private KafkaProducerMessage kafkaProducerMessage;

    List<ObjectId> sellerIds = Arrays.asList(
            new ObjectId("66080feaf1152b798d470aee"),
            new ObjectId("660810848a754e0c32d6ca2a"),
            new ObjectId("66080f72f1152b798d470ae8"),
            new ObjectId("66080ebef1152b798d470adf"),
            new ObjectId("6608139e49bab06504dfd48b")
    );

    @Scheduled(fixedRate = 60000)
    public void sendMessage() {
        for (int count = 0; count < 5; count++) {
            OrderDTO order = getOrderDTO(sellerIds.get(count));

            System.out.println("====== Sending order to topic: " + kafkaProducerMessage.getNameKafkaTopic() + " ======");
            kafkaProducerMessage.sendMessage(order);
        }
    }

    private static OrderDTO getOrderDTO(ObjectId sellerId) {
        ItemDTO item = new ItemDTO();
        item.setItemId("ABC0123456789");
        item.setSku("SKU_DO_SELLER_123");
        item.setName("Desempenador de pipa");
        item.setDescription("O melhor desempenador de pipa que você verá na sua vida.\nTração 4x4 e efeitos sonoros ideais para o seu churrasco");
        item.setPrice(1499.99);
        item.setUrl("https://www.minha-lojinha.com.br/products/ABC0123456789");

        BuyerDTO buyer = new BuyerDTO();
        buyer.setId("BCD2F2CCBA");
        buyer.setName("Pietro Alcantara");
        buyer.setEmail("p2alcantara_teste@gmail.com");

        AddressDTO shippingAddress = new AddressDTO();
        shippingAddress.setPostalCode("04540-010");
        shippingAddress.setStreetName("Rua do bobos");
        shippingAddress.setNumber("456 A");
        shippingAddress.setAdditionalInfo("Apto 37");

        AddressDTO billingAddress = new AddressDTO();
        billingAddress.setPostalCode("03333-310");
        billingAddress.setStreetName("Rua do sellers");
        billingAddress.setNumber("333");
        billingAddress.setAdditionalInfo("Próximo ao metrô cacimbas");

        PaymentDTO payment = new PaymentDTO();
        payment.setMethod(getRandomEnumValue(PaymentMethod.class));
        payment.setAmount(1499.99);
        payment.setStatus(getRandomEnumValue(PaymentStatus.class));

        OrderDTO order = new OrderDTO();
        order.setOrderId(String.valueOf(new ObjectId()));
        order.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        order.setUpdatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        order.setStatus(getRandomEnumValue(OrderStatus.class));
        order.setItems(Collections.singletonList(item));
        order.setSeller(String.valueOf(sellerId));
        order.setBuyer(buyer);
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        order.setPayment(payment);
        return order;
    }

    private static <T extends Enum<?>> T getRandomEnumValue(Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        int randomIndex = ThreadLocalRandom.current().nextInt(enumConstants.length);
        return enumConstants[randomIndex];
    }
}
