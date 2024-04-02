package br.com.processor.order.message.consumer;

import br.com.processor.order.entity.Order;
import br.com.processor.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerMessage {

    private final Logger LOG = LoggerFactory.getLogger(KafkaConsumerMessage.class);

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "order-post-topic", groupId = "order-processor-posts-group")
    public void listening(@Payload Order order) {
        System.out.println("Received Order information : " + order);
        LOG.info("Order Processor - Received Order Post information: {}", order);

        orderService.saveOrder(order);
    }
}
