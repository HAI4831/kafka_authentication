// KafkaConsumerService.java
package com.run.serviceb;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "topic_a", groupId = "group_b")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
