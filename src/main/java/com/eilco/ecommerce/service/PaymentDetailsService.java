package com.eilco.ecommerce.service;

import com.eilco.ecommerce.dto.PaymentDetailsRequest;
import com.eilco.ecommerce.dto.PaymentDetailsResponse;
import com.eilco.ecommerce.model.entities.Order;
import com.eilco.ecommerce.model.entities.PaymentDetails;
import com.eilco.ecommerce.model.entities.Product;
import com.eilco.ecommerce.repository.OrderRepository;
import com.eilco.ecommerce.repository.PaymentDetailsRepository;
import com.eilco.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentDetailsService {

    private final PaymentDetailsRepository paymentDetailsRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public PaymentDetailsResponse save(PaymentDetailsRequest request) {
        PaymentDetails paymentDetails = convertRequestToEntity(request);

        PaymentDetails savedPayment = paymentDetailsRepository.save(paymentDetails);

        if ("DONE".equalsIgnoreCase(request.getPaymentStatus())) {
            updateProductQuantities(paymentDetails.getOrder());
        }

        return convertEntityToResponse(savedPayment);
    }

    private void updateProductQuantities(Order order) {
        order.getOrderItems().forEach(orderItem -> {
            Product product = orderItem.getProduct();

            int newQuantity = product.getQuantity() - orderItem.getQuantity();
            if (newQuantity < 0) {
                throw new IllegalStateException("Insufficient stock for product: " + product.getName());
            }

            product.setQuantity(newQuantity);

            if (newQuantity == 0) {
                product.setActive(false);
            }

            productRepository.save(product);
        });
    }

    private PaymentDetails convertRequestToEntity(PaymentDetailsRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + request.getOrderId()));

        return PaymentDetails.builder()
                .order(order)
                .paymentType(request.getPaymentType())
                .paymentStatus(request.getPaymentStatus())
                .paymentDate(request.getPaymentDate())
                .build();
    }

    public PaymentDetailsResponse convertEntityToResponse(PaymentDetails paymentDetails) {
        return PaymentDetailsResponse.builder()
                .paymentId(paymentDetails.getPaymentId())
                .orderId(paymentDetails.getOrder().getOrderId())
                .paymentType(paymentDetails.getPaymentType())
                .paymentStatus(paymentDetails.getPaymentStatus())
                .paymentDate(paymentDetails.getPaymentDate())
                .build();
    }
    public Optional<PaymentDetails> findById(Long id) {
        return paymentDetailsRepository.findById(id);
    }

    public List<PaymentDetails> findAll() {
        return paymentDetailsRepository.findAll();
    }

    public void deleteById(Long id) {
        paymentDetailsRepository.deleteById(id);
    }


}
