package com.reactive.webflux.sec01.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Table(name = "customer_order")
public class CustomerOrder {

    @Id
    UUID orderId;
    int customerId;
    int productId;
    Instant orderDate;
}
