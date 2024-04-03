package br.com.processor.order.unit.producer;

import br.com.processor.order.message.producer.KafkaProducerMessage;
import br.com.processor.order.message.producer.MarktplaceProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MarktplaceProducerTest {

    @InjectMocks
    private MarktplaceProducer marktplaceProducer;

    @Mock
    private KafkaProducerMessage kafkaProducerMessage;

    @Test
    public void sendMessageExecutedWithSuccess() {
        marktplaceProducer.sendMessage();
    }
}
