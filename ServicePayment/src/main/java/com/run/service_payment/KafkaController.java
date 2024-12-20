// KafkaController.java
package com.run.service_payment;

import com.run.service_payment.kafka.KafkaProducerService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducerService producerService;

    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public Mono<Void> sendMessage(@RequestBody KafkaMessage kafkaMessage) {
        return producerService.sendMessage(kafkaMessage.getTopic(), kafkaMessage.getKey(), kafkaMessage.getMessage());
    }
}
