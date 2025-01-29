package com.reactive.webflux.course.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public record OrderDetails(UUID orderId,
                           String name,
                           String description,
                           int amount,
                           Instant orderDate){}