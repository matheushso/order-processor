package br.com.processor.order.message.producer;

import br.com.processor.order.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerMessage {

    @Autowired
    private KafkaTemplate<String, OrderDTO> kafkaTemplate;

    private final String KAFKA_TOPIC = "order-post-topic";

    public void sendMessage(OrderDTO orderDTO) {
        kafkaTemplate.send(KAFKA_TOPIC, orderDTO);
    }

    public String getNameKafkaTopic() {
        return KAFKA_TOPIC;
    }
}
