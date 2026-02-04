package com.phuoc.carRental.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethodRequest {

    @NotBlank(message = "PAYMENT_METHOD_NAME_REQUIRED")
    String name;
}
