// KafkaConsumerService.java
package com.run.servicea;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "topic_b", groupId = "group_a")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
