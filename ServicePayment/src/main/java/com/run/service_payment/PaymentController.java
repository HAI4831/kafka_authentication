package com.run.service_payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentController(PaymentService paymentService, PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping("/process")
    public Mono<Payment> processPayment(@RequestBody Payment payment) {
        return paymentService.processPayment(payment);
    }

//    @GetMapping("/{orderId}")
//    public Mono<Payment> getPaymentByOrderId(@PathVariable String orderId) {
//        return paymentRepository.findByOrderId(orderId);
//    }

    @GetMapping
    public Flux<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
