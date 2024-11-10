package com.reactive.webflux.sec01.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "product")
@Data
public class Product {
    @Id
    int id;
    String description;
    int price;
}
