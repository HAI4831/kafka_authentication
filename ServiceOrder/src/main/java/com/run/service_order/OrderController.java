// OrderController.java
package com.run.service_order;

import com.run.service_order.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final KafkaProducerService producerService;
    private final OrderService orderService;

    @Autowired
    public OrderController(KafkaProducerService producerService, OrderService orderService) {
        this.producerService = producerService;
        this.orderService = orderService;
    }
    @PostMapping("/create")
    public Mono<Void> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
}
