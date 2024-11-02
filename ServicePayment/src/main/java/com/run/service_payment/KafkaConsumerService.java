//// KafkaConsumerService.java
//package com.run.service_payment;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaConsumerService {
//
//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//
//    @KafkaListener(topics = "topic_order", groupId = "${spring.kafka.consumer.group-id}")
//    public void listen(String message) {
//        System.out.println("Received message: " + message);
//    }
//}
