package com.reactive.webflux.repository;

import com.reactive.webflux.AbstractTest;
import com.reactive.webflux.course.model.OrderDetails;
import com.reactive.webflux.course.repository.CustomerOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class CustomerOrderRepositoryTest extends AbstractTest {

    @Autowired
    CustomerOrderRepository customerOrderRepository;

    @Test
    public void testOder(){
        var data =customerOrderRepository.findOrderDetails("iphone 20").map(OrderDetails::amount);
        data.subscribe(System.out::println);
    }

}
