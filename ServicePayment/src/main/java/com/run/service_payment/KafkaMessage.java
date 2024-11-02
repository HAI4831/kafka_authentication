package com.run.service_payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class KafkaMessage {
    private String topic;
    private String key;
    private String message;
}
