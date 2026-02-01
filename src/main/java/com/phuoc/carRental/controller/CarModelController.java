package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.CarModelRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.CarModelResponse;
import com.phuoc.carRental.service.CarModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/car-models")
@Tag(name = "Car Model Controller", description = "Quản lý mẫu xe")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarModelController {

    CarModelService carModelService;

    @Operation(summary = "Tạo mẫu xe")
    @PostMapping
    public ApiCustomResponse<Void> create(@RequestBody CarModelRequest request) {
        carModelService.create(request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Create car model successfully")
                .build();
    }

    @Operation(summary = "Lấy danh sách mẫu xe")
    @GetMapping
    public ApiCustomResponse<List<CarModelResponse>> getAll() {
        return ApiCustomResponse.<List<CarModelResponse>>builder()
                .code(2000)
                .data(carModelService.getAll())
                .build();
    }

    @Operation(summary = "Lấy mẫu xe theo ID")
    @GetMapping("/{modelId}")
    public ApiCustomResponse<CarModelResponse> getById(@PathVariable UUID modelId) {
        return ApiCustomResponse.<CarModelResponse>builder()
                .code(2000)
                .data(carModelService.getById(modelId))
                .build();
    }

    @Operation(summary = "Cập nhật mẫu xe")
    @PatchMapping("/{modelId}")
    public ApiCustomResponse<Void> update(
            @PathVariable UUID modelId,
            @RequestBody CarModelRequest request
    ) {
        carModelService.update(modelId, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Update car model successfully")
                .build();
    }

    @Operation(summary = "Xóa mẫu xe")
    @DeleteMapping("/{modelId}")
    public ApiCustomResponse<Void> delete(@PathVariable UUID modelId) {
        carModelService.delete(modelId);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Delete car model successfully")
                .build();
    }
}
