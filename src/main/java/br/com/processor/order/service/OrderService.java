package br.com.processor.order.service;

import br.com.processor.order.entity.Order;
import br.com.processor.order.enums.EnumValidator;
import br.com.processor.order.enums.OrderStatus;
import br.com.processor.order.enums.PaymentMethod;
import br.com.processor.order.enums.PaymentStatus;
import br.com.processor.order.exception.DateFormatException;
import br.com.processor.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AuthService authService;

    public void saveOrder(Order order) {
        orderRepository.saveOrder(order);
    }

    public List<Order> findOrdersWithOptionalFilters(String auth, LocalDate startDate, LocalDate endDate,
                                                     String orderStatus, String paymentMethod, String paymentStatus) {
        String sellerId = authService.validateAuthAndDecode(auth);
        validateDate(startDate, endDate);
        validateEnums(orderStatus, paymentMethod, paymentStatus);
        return orderRepository.findOrdersWithOptionalFilters(sellerId, startDate, endDate,
                orderStatus, paymentMethod, paymentStatus);
    }

    private void validateDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return;
        }

        if ((startDate != null && endDate == null) || (startDate == null && endDate != null)) {
            throw new DateFormatException("Deve ser informado data inicial e data final.");
        }

        if (startDate.isAfter(endDate)) {
            throw new DateFormatException();
        }
    }

    private void validateEnums(String orderStatus, String paymentMethod, String paymentStatus) {
        EnumValidator.validateEnum(OrderStatus.class, orderStatus);
        EnumValidator.validateEnum(PaymentMethod.class, paymentMethod);
        EnumValidator.validateEnum(PaymentStatus.class, paymentStatus);
    }
}
