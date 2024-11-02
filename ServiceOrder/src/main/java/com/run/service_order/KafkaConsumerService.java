//package com.run.service_order;
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
//    @KafkaListener(topics = "topic_payment", groupId = "${spring.kafka.consumer.group-id}")
//    public void listen(String message) {
//        System.out.println("Received message: " + message);
//        // Assuming message is in JSON format and you can convert it to Order
//        Order order = parseMessageToOrder(message); // Implement this method to parse JSON
//
//        // Create a payment invoice
//        PaymentInvoice invoice = paymentService.createPaymentInvoice(order);
//
//        // Update the order status to "paid"
//        orderService.updateOrderStatus(order.getOrderId(), "paid");
//    }
//    private Order parseMessageToOrder(String message) {
//        // Implement your JSON parsing logic here (e.g., using Jackson or Gson)
//        // This is just a placeholder
//        return new Order(); // Return a populated order object
//    }
//}
