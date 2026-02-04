package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.PaymentMethodRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.PaymentMethodResponse;
import com.phuoc.carRental.service.PaymentMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment-methods")
@Tag(
        name = "Payment Method",
        description = "Quản lý phương thức thanh toán"
)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentMethodController {

    PaymentMethodService paymentMethodService;

    @Operation(
            summary = "Tạo Payment Method",
            description = "Tạo mới phương thức thanh toán"
    )
    @PostMapping
    public ApiCustomResponse<Void> create(
            @RequestBody PaymentMethodRequest request
    ) {
        paymentMethodService.create(request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Create payment method successfully")
                .build();
    }

    @Operation(
            summary = "Lấy danh sách Payment Method",
            description = "Lấy toàn bộ phương thức thanh toán"
    )
    @GetMapping
    public ApiCustomResponse<List<PaymentMethodResponse>> getAll() {
        return ApiCustomResponse.<List<PaymentMethodResponse>>builder()
                .code(2000)
                .data(paymentMethodService.getAll())
                .build();
    }

    @Operation(
            summary = "Lấy Payment Method theo ID",
            description = "Lấy chi tiết phương thức thanh toán"
    )
    @GetMapping("/{id}")
    public ApiCustomResponse<PaymentMethodResponse> getById(
            @PathVariable UUID id
    ) {
        return ApiCustomResponse.<PaymentMethodResponse>builder()
                .code(2000)
                .data(paymentMethodService.getById(id))
                .build();
    }

    @Operation(
            summary = "Cập nhật Payment Method",
            description = "Cập nhật phương thức thanh toán theo id"
    )
    @PatchMapping("/{id}")
    public ApiCustomResponse<Void> update(
            @PathVariable UUID id,
            @RequestBody PaymentMethodRequest request
    ) {
        paymentMethodService.update(id, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Update payment method successfully")
                .build();
    }

    @Operation(
            summary = "Xóa Payment Method",
            description = "Xóa phương thức thanh toán theo id"
    )
    @DeleteMapping("/{id}")
    public ApiCustomResponse<Void> delete(
            @PathVariable UUID id
    ) {
        paymentMethodService.delete(id);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Delete payment method successfully")
                .build();
    }
}
