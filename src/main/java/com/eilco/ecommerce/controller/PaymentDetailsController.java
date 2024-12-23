package com.eilco.ecommerce.controller;

import com.eilco.ecommerce.dto.PaymentDetailsRequest;
import com.eilco.ecommerce.dto.PaymentDetailsResponse;
import com.eilco.ecommerce.service.PaymentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payment-details")
@RequiredArgsConstructor
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDetailsResponse addPaymentDetails(@RequestBody PaymentDetailsRequest request) {
        return paymentDetailsService.save(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetailsResponse> getPaymentDetails(@PathVariable Long id) {
        return paymentDetailsService.findById(id)
                .map(paymentDetailsService::convertEntityToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<PaymentDetailsResponse> listPaymentDetails() {
        return paymentDetailsService.findAll().stream()
                .map(paymentDetailsService::convertEntityToResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaymentDetails(@PathVariable Long id) {
        paymentDetailsService.deleteById(id);
    }
}
