package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.AreaRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.AreaResponse;
import com.phuoc.carRental.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/areas")
@Tag(name = "Area Controller", description = "Quản lý khu vực")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaController {
    AreaService areaService;

    @Operation(summary = "Tạo khu vực mới")
    @PostMapping
    public ApiCustomResponse<Void> create(@RequestBody AreaRequest request) {
        areaService.create(request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Create area successfully")
                .build();
    }

    @Operation(summary = "Lấy danh sách khu vực")
    @GetMapping
    public ApiCustomResponse<List<AreaResponse>> getAll() {
        return ApiCustomResponse.<List<AreaResponse>>builder()
                .code(2000)
                .data(areaService.getAll())
                .build();
    }

    @Operation(summary = "Lấy khu vực theo ID")
    @GetMapping("/{areaId}")
    public ApiCustomResponse<AreaResponse> getById(@PathVariable UUID areaId) {
        return ApiCustomResponse.<AreaResponse>builder()
                .code(2000)
                .data(areaService.getById(areaId))
                .build();
    }

    @Operation(summary = "Cập nhật khu vực")
    @PatchMapping("/{areaId}")
    public ApiCustomResponse<Void> update(
            @PathVariable UUID areaId,
            @RequestBody AreaRequest request
    ) {
        areaService.update(areaId, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Update area successfully")
                .build();
    }

    @Operation(summary = "Xóa khu vực")
    @DeleteMapping("/{areaId}")
    public ApiCustomResponse<Void> delete(@PathVariable UUID areaId) {
        areaService.delete(areaId);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Delete area successfully")
                .build();
    }
}
