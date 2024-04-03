package br.com.processor.order.unit.service;

import br.com.processor.order.entity.Order;
import br.com.processor.order.enums.OrderStatus;
import br.com.processor.order.enums.PaymentMethod;
import br.com.processor.order.enums.PaymentStatus;
import br.com.processor.order.exception.DateFormatException;
import br.com.processor.order.repository.OrderRepository;
import br.com.processor.order.service.AuthService;
import br.com.processor.order.service.OrderService;
import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AuthService authService;

    private static final String DATE_MSG_ERROR = "A data informada é inválida.";
    private static final String DATE_MSG_ERROR_WHEN_ONLY_ONE_DATE_PROVIDED = "Deve ser informado data inicial e data final.";

    @Test
    public void saveOrderReturnSuccess() {
        Order order = new Order();
        doNothing().when(orderRepository).saveOrder(order);

        orderService.saveOrder(order);

        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    public void findOrdersWithOptionalFiltersWhenNoFilterFieldsProvidedReturnOrdersWithSuccess() {
        String auth = "auth";
        when(authService.validateAuthAndDecode(auth)).thenReturn(ConstantsTests.SELLER_ID);

        orderService.findOrdersWithOptionalFilters(auth, null, null, null, null, null);

        verify(authService, times(1)).validateAuthAndDecode(auth);
        verify(orderRepository, times(1)).findOrdersWithOptionalFilters(ConstantsTests.SELLER_ID, null, null, null, null, null);
    }

    @Test
    public void findOrdersWithOptionalFiltersWhenAllFieldsProvidedReturnOrdersWithSuccess() {
        String auth = "auth";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        String orderStatus = OrderStatus.NEW.toString();
        String paymentMethod = PaymentMethod.CREDIT.toString();
        String paymentStatus = PaymentStatus.APPROVED.toString();
        when(authService.validateAuthAndDecode(auth)).thenReturn(ConstantsTests.SELLER_ID);

        orderService.findOrdersWithOptionalFilters(auth, startDate, endDate, orderStatus, paymentMethod, paymentStatus);

        verify(authService, times(1)).validateAuthAndDecode(auth);
        verify(orderRepository, times(1)).findOrdersWithOptionalFilters(ConstantsTests.SELLER_ID, startDate, endDate, orderStatus, paymentMethod, paymentStatus);
    }

    @Test
    public void findOrdersWithOptionalFiltersWhenInvalidDatesReturnDateFormatException() {
        String auth = "auth";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(1);
        when(authService.validateAuthAndDecode(auth)).thenReturn(ConstantsTests.SELLER_ID);

        DateFormatException ex = assertThrows(DateFormatException.class, () -> orderService.findOrdersWithOptionalFilters(auth, startDate, endDate, null, null, null));

        assertEquals(DATE_MSG_ERROR, ex.getMessage());

        verify(authService, times(1)).validateAuthAndDecode(auth);
        verify(orderRepository, times(0)).findOrdersWithOptionalFilters(ConstantsTests.SELLER_ID, startDate, endDate, null, null, null);
    }

    @Test
    public void findOrdersWithOptionalFiltersWhenOnlyStartDateIsProvidedReturnDateFormatException() {
        String auth = "auth";
        LocalDate startDate = LocalDate.now();
        when(authService.validateAuthAndDecode(auth)).thenReturn(ConstantsTests.SELLER_ID);

        DateFormatException ex = assertThrows(DateFormatException.class, () -> orderService.findOrdersWithOptionalFilters(auth, startDate, null, null, null, null));

        assertEquals(DATE_MSG_ERROR_WHEN_ONLY_ONE_DATE_PROVIDED, ex.getMessage());

        verify(authService, times(1)).validateAuthAndDecode(auth);
        verify(orderRepository, times(0)).findOrdersWithOptionalFilters(ConstantsTests.SELLER_ID, startDate, null, null, null, null);
    }

    @Test
    public void findOrdersWithOptionalFiltersWhenOnlyEndDateIsProvidedReturnDateFormatException() {
        String auth = "auth";
        LocalDate endDate = LocalDate.now();
        when(authService.validateAuthAndDecode(auth)).thenReturn(ConstantsTests.SELLER_ID);

        DateFormatException ex = assertThrows(DateFormatException.class, () -> orderService.findOrdersWithOptionalFilters(auth, null, endDate, null, null, null));

        assertEquals(DATE_MSG_ERROR_WHEN_ONLY_ONE_DATE_PROVIDED, ex.getMessage());

        verify(authService, times(1)).validateAuthAndDecode(auth);
        verify(orderRepository, times(0)).findOrdersWithOptionalFilters(ConstantsTests.SELLER_ID, null, endDate, null, null, null);
    }
}
