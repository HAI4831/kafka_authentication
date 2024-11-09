package com.run.service_order;

import lombok.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.run.service_order.kafka.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON serialization/deserialization

    public Mono<Void> createOrder(Order order) {
        return orderRepository.save(order)
                .flatMap(savedOrder -> {
                    KafkaMessage orderCreatedMessage = new KafkaMessage("topic_order", savedOrder.getId().toString(), toJson(savedOrder));
                    return kafkaProducerService.sendMessage(orderCreatedMessage.getTopic(), orderCreatedMessage.getKey(), orderCreatedMessage.getMessage());
                });
    }

    @KafkaListener(topics = "topic_payment", groupId = "order_group")
    public void listenToOrderCreateRequest(String message) {
        // Use Mono.defer() to keep it non-blocking and chain reactive processing
        Mono.defer(() -> {
            System.out.println("message payment call order:" + message);
            OrderCreateRequest orderCreateRequest;
            try {
                // Deserialize message to OrderCreateRequest
                orderCreateRequest = fromJson(message, OrderCreateRequest.class);
            } catch (Exception e) {
                // Handle parsing error with proper logging
                System.err.println("Failed to parse JSON message: " + e.getMessage());
                System.err.println("Original message: " + message);
                return Mono.empty(); // Ignore this message and stop processing
            }

            // Create an Order object
            Order order = new Order(
                    orderCreateRequest.getId(),
                    orderCreateRequest.getCustomerId(),
                    orderCreateRequest.getPrice(),
                    "CREATED" // Set initial order status
            );

            // Save the order and chain the operations reactively
            return orderRepository.save(order)
                    .flatMap(savedOrder -> {
                        // Create a success message after saving the order
                        OrderCreateSuccess orderCreateSuccess = new OrderCreateSuccess(
                                savedOrder.getId(),
                                "Order created successfully"
                        );

                        // Convert success message to JSON
                        String orderCreateSuccessJson = toJson(orderCreateSuccess);

                        // Send success message to Kafka topic
                        return kafkaProducerService.sendMessage("topic_order",
                                savedOrder.getId().toString(),
                                orderCreateSuccessJson);
                    });
        }).subscribe();
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderCreateRequest {
        private Long id;
        private Long paymentId;
        private Long customerId;
        private double price;
        private String status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderCreateSuccess {
        private Long orderId;
        private String message;
    }

    // Convert an object to a JSON string
    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    // Convert a JSON string to an object
    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }

    public static void main(String[] args) {
        OrderService service = new OrderService(null, null);
        String testMessage = "{\"id\":1,\"customerId\":2,\"price\":37000.0,\"status\":\"COMPLETED\"}";
        try {
            OrderCreateRequest request = service.fromJson(testMessage, OrderCreateRequest.class);
            System.out.println("Deserialized: " + request);
        } catch (Exception e) {
            System.err.println("Deserialization error: " + e.getMessage());
        }
    }

}
