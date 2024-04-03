package br.com.processor.order.unit.repository;

import br.com.processor.order.entity.Order;
import br.com.processor.order.enums.OrderStatus;
import br.com.processor.order.enums.PaymentMethod;
import br.com.processor.order.enums.PaymentStatus;
import br.com.processor.order.repository.OrderRepository;
import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @InjectMocks
    private OrderRepository orderRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    public void saveOrderReturnSuccess() {
        Order order = new Order();

        orderRepository.saveOrder(order);

        verify(mongoTemplate, times(1)).save(order);
    }

    @Test
    public void findOrdersWithOptionalFiltersReturnSuccess() {
        String sellerId = ConstantsTests.SELLER_ID;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        String orderStatus = OrderStatus.NEW.toString();
        String paymentMethod = PaymentMethod.CREDIT.toString();
        String paymentStatus = PaymentStatus.APPROVED.toString();

        orderRepository.findOrdersWithOptionalFilters(sellerId, startDate, endDate, orderStatus, paymentMethod, paymentStatus);

        verify(mongoTemplate, times(1)).find(any(), eq(Order.class));
    }

    @Test
    public void findOrdersWithOptionalFiltersWhenAllOptionalsFieldsNotProvidedReturnSuccess() {
        String sellerId = ConstantsTests.SELLER_ID;

        Query query = orderRepository.getQuery(sellerId, null, null, null, null, null);

        assertEquals("Query: { \"seller\" : \"660810848a754e0c32d6ca2a\"}, Fields: {}, Sort: {}", query.toString());
    }

    @Test
    public void findOrdersWithOptionalFiltersWhenFieldStartDateNotProvidedReturnSuccess() {
        String sellerId = ConstantsTests.SELLER_ID;
        LocalDate endDate = LocalDate.now();
        String orderStatus = OrderStatus.NEW.toString();
        String paymentMethod = PaymentMethod.CREDIT.toString();
        String paymentStatus = PaymentStatus.APPROVED.toString();

        Query query = orderRepository.getQuery(sellerId, null, endDate, orderStatus, paymentMethod, paymentStatus);

        assertEquals("Query: { \"seller\" : \"660810848a754e0c32d6ca2a\", \"status\" : \"NEW\", \"payment.method\" : \"CREDIT\", " +
                "\"payment.status\" : \"APPROVED\"}, Fields: {}, Sort: {}", query.toString());
    }

    @Test
    public void findOrdersWithOptionalFiltersWhenFieldEndDateNotProvidedReturnSuccess() {
        String sellerId = ConstantsTests.SELLER_ID;
        LocalDate startDate = LocalDate.now();
        String orderStatus = OrderStatus.NEW.toString();
        String paymentMethod = PaymentMethod.CREDIT.toString();
        String paymentStatus = PaymentStatus.APPROVED.toString();

        Query query = orderRepository.getQuery(sellerId, startDate, null, orderStatus, paymentMethod, paymentStatus);

        assertEquals("Query: { \"seller\" : \"660810848a754e0c32d6ca2a\", \"status\" : \"NEW\", \"payment.method\" : \"CREDIT\", " +
                "\"payment.status\" : \"APPROVED\"}, Fields: {}, Sort: {}", query.toString());
    }

    @Test
    public void findOrdersWithOptionalFiltersWhenAllFieldsProvidedReturnSuccess() {
        String sellerId = ConstantsTests.SELLER_ID;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        String orderStatus = OrderStatus.NEW.toString();
        String paymentMethod = PaymentMethod.CREDIT.toString();
        String paymentStatus = PaymentStatus.APPROVED.toString();

        Query query = orderRepository.getQuery(sellerId, startDate, endDate, orderStatus, paymentMethod, paymentStatus);

        assertEquals("Query: { \"seller\" : \"660810848a754e0c32d6ca2a\", \"createdAt\" : { \"$gte\" : 1712102400000, \"$lt\" : 1712188799999}," +
                " \"status\" : \"NEW\", \"payment.method\" : \"CREDIT\", \"payment.status\" : \"APPROVED\"}, Fields: {}, Sort: {}", query.toString());
    }
}
