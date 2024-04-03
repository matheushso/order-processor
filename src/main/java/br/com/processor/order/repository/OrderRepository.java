package br.com.processor.order.repository;

import br.com.processor.order.entity.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

@Repository
public class OrderRepository {

    private final MongoTemplate mongoTemplate;

    public OrderRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void saveOrder(Order order) {
        mongoTemplate.save(order);
    }

    public List<Order> findOrdersWithOptionalFilters(String seller, LocalDate startDate, LocalDate endDate,
                                                     String orderStatus, String paymentMethod, String paymentStatus) {
        Query query = getQuery(seller, startDate, endDate, orderStatus, paymentMethod, paymentStatus);

        return mongoTemplate.find(query, Order.class);
    }

    public Query getQuery(String seller, LocalDate startDate, LocalDate endDate, String orderStatus, String paymentMethod, String paymentStatus) {
        Query query = new Query();

        query.addCriteria(Criteria.where("seller").is(seller));

        if (startDate != null && endDate != null) {
            long startTimestamp = startDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            long endTimestamp = endDate.atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC).toEpochMilli();

            query.addCriteria(Criteria.where("createdAt")
                    .gte(startTimestamp)
                    .lt(endTimestamp));
        }

        if (orderStatus != null) {
            query.addCriteria(Criteria.where("status").is(orderStatus.toUpperCase()));
        }

        if (paymentMethod != null) {
            query.addCriteria(Criteria.where("payment.method").is(paymentMethod.toUpperCase()));
        }

        if (paymentStatus != null) {
            query.addCriteria(Criteria.where("payment.status").is(paymentStatus.toUpperCase()));
        }

        return query;
    }
}
