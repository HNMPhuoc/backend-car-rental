package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.CarBrandRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.CarBrandResponse;
import com.phuoc.carRental.service.CarBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/car-brands")
@Tag(name = "Car Brand Controller", description = "Quản lý hãng xe")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarBrandController {

    CarBrandService carBrandService;

    @Operation(summary = "Tạo hãng xe")
    @PostMapping
    public ApiCustomResponse<Void> create(@RequestBody CarBrandRequest request) {
        carBrandService.create(request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Create car brand successfully")
                .build();
    }

    @Operation(summary = "Lấy danh sách hãng xe")
    @GetMapping
    public ApiCustomResponse<List<CarBrandResponse>> getAll() {
        return ApiCustomResponse.<List<CarBrandResponse>>builder()
                .code(2000)
                .data(carBrandService.getAll())
                .build();
    }

    @Operation(summary = "Lấy hãng xe theo ID")
    @GetMapping("/{brandId}")
    public ApiCustomResponse<CarBrandResponse> getById(@PathVariable UUID brandId) {
        return ApiCustomResponse.<CarBrandResponse>builder()
                .code(2000)
                .data(carBrandService.getById(brandId))
                .build();
    }

    @Operation(summary = "Cập nhật hãng xe")
    @PatchMapping("/{brandId}")
    public ApiCustomResponse<Void> update(
            @PathVariable UUID brandId,
            @RequestBody CarBrandRequest request
    ) {
        carBrandService.update(brandId, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Update car brand successfully")
                .build();
    }

    @Operation(summary = "Xóa hãng xe")
    @DeleteMapping("/{brandId}")
    public ApiCustomResponse<Void> delete(@PathVariable UUID brandId) {
        carBrandService.delete(brandId);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Delete car brand successfully")
                .build();
    }
}
