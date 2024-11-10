package com.reactive.webflux.sec01.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public record OrderDetails(UUID orderId,
                           String name,
                           String description,
                           int amount,
                           Instant orderDate){}