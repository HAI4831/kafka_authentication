//package com.run.kafkaauthentication;
//
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import java.util.Properties;
//
//public class KafkaConfig {
//
//    public Properties getProducerProperties() {
//        Properties props = new Properties();
//        // Tải các thuộc tính từ tệp cấu hình chung
//        props.load(new FileInputStream("path/to/kafka-config.properties"));
//        return props;
//    }
//
//    public Properties getConsumerProperties() {
//        Properties props = new Properties();
//        // Tải các thuộc tính từ tệp cấu hình chung
//        props.load(new FileInputStream("path/to/kafka-config.properties"));
//        return props;
//    }
//}
