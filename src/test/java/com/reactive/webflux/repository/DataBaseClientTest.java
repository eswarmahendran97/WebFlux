package com.reactive.webflux.repository;

import com.reactive.webflux.AbstractTest;
import com.reactive.webflux.course.model.OrderDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;

public class DataBaseClientTest extends AbstractTest {

    @Autowired
    DatabaseClient databaseClient;

    @Test
    public void testClient(){

        var query = """
                SELECT
                co.order_id,
                c.name AS customer_name,
                p.description AS product_name,
                co.amount,
                co.order_date
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON p.id = co.product_id
            WHERE
                p.description = :description
            ORDER BY co.amount DESC
                """;

        var data = databaseClient.sql(query).bind("description","iphone 20")
                .mapProperties(OrderDetails.class).all().map(OrderDetails::amount);
        data.subscribe(System.out::println);
    }
}
