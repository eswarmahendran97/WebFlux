package com.reactive.webflux.course.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "product")
@ToString
public class Product {
    @Id
    int id;
    String description;
    int price;

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public int getPrice(){
        return price;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setDescription(String description){
        this.description= description;
    }

    public void setPrice(int price){
        this.price = price;
    }


}
