package com.run.service_payment;

import com.run.service_payment.kafka.KafkaProducerService;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Mono<Payment> processPayment(Payment payment) {
        return paymentRepository.save(payment)
                .flatMap(savedPayment -> {
                    // Create an OrderCreateRequest from the saved payment
                    OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                            null,
                            savedPayment.getId(),
                            savedPayment.getCustomerId(),
                            savedPayment.getAmount(),
                            "COMPLETED" // assuming the payment was successful
                    );

                    // Convert OrderCreateRequest to JSON
                    String orderCreateRequestJson = toJson(orderCreateRequest);

                    // Send the message to Kafka
                    KafkaMessage orderCreateMessage = new KafkaMessage("topic_payment",
                            savedPayment.getId().toString(),
                            orderCreateRequestJson
                    );

                    return kafkaProducerService.sendMessage(
                                    orderCreateMessage.getTopic(),
                                    orderCreateMessage.getKey(),
                                    orderCreateMessage.getMessage())
                            .thenReturn(savedPayment);
                });
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
    @KafkaListener(topics = "topic_order", groupId = "group_payment")
    public void OrderCallPayment(String message) {
        Mono.defer(()->{
            System.out.println("message order call payment:" + message);

            Order order;
            try {
                order = fromJson(message, Order.class);
            } catch (Exception e) {
                // Handle parsing error
                System.err.println("Failed to parse JSON message: " + e.getMessage());
                // Optionally, log the message for debugging
                System.err.println("Original message: " + message);
                // Return an empty Mono to signal completion without processing further
                return Mono.empty();
            }
            return Mono.empty();
//            Payment payment = new Payment(null, order.getCustomerId(), order.getPrice(), "paid");
//            return paymentRepository.save(payment);

//            return paymentRepository.save(payment)
//                    .flatMap(savedPayment -> {
//                        KafkaMessage paymentCreatedMessage = new KafkaMessage("topic_order", savedPayment.getId().toString(), toJson(savedPayment));
//                        return kafkaProducerService.sendMessage(paymentCreatedMessage.getTopic(), paymentCreatedMessage.getKey(), paymentCreatedMessage.getMessage());
//                    })
//                    .onErrorResume(ex -> {
//                        // Handle any exceptions during saving or message sending
//                        System.err.println("Error during payment processing or message sending: " + ex.getMessage());
//                        return Mono.empty();
//                    });
        }).subscribe();
    }


//    @KafkaListener(topics = "topic_order", groupId = "order_group")
//    public void processOrderCreated(String message) {
//        System.out.println("message process payment:" +message);
//        OrderRequest orderRequest = fromJson(message, OrderRequest.class);
//        Payment payment = new Payment(null,orderRequest.getOrderId(), calculateAmount(orderRequest), "PAID");
//        processPayment(payment).subscribe();
//    }
//
//    private double calculateAmount(OrderRequest orderRequest) {
//        return orderRequest.getAmount();
//    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class PaymentRequest {
//        private Long orderId;
//        private double amount;
//        private String status;
//    }
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class OrderRequest {
//        private Long orderId;
//        private double amount;
//        private String status;
//    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Order {
        private Long id;
        private Long customerId;
        private double price;
        private String status;
    }
}
