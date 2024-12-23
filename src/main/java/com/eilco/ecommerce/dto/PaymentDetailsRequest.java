package com.eilco.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsRequest {
    private Long orderId;
    private String paymentType;
    private String paymentStatus;
    private LocalDateTime paymentDate;
}
