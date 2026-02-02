package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.CarTypeRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.CarTypeResponse;
import com.phuoc.carRental.service.CarTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/car-types")
@Tag(
        name = "Car Type Controller",
        description = "Quản lý loại xe (Car Type)"
)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarTypeController {

    CarTypeService carTypeService;

    @Operation(
            summary = "Tạo car type mới",
            description = """
                    Tạo mới một car type theo model.
                    - Name không được trùng trong cùng model
                    - Dùng cho admin
                    """
    )
    @PostMapping
    public ApiCustomResponse<Void> create(@RequestBody @Valid CarTypeRequest request) {
        carTypeService.create(request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Create car type successfully")
                .build();
    }

    @Operation(
            summary = "Lấy danh sách car type",
            description = """
                    Lấy danh sách tất cả car type.
                    - Không phân trang
                    - Dùng cho màn hình quản trị
                    """
    )
    @GetMapping
    public ApiCustomResponse<List<CarTypeResponse>> getAll() {
        return ApiCustomResponse.<List<CarTypeResponse>>builder()
                .code(2000)
                .data(carTypeService.getAll())
                .build();
    }

    @Operation(
            summary = "Lấy car type theo ID",
            description = """
                    Lấy chi tiết car type theo id.
                    - Dùng khi mở màn hình view / edit
                    - Trả về lỗi nếu không tồn tại
                    """
    )
    @GetMapping("/{typeId}")
    public ApiCustomResponse<CarTypeResponse> getById(@PathVariable UUID typeId) {
        return ApiCustomResponse.<CarTypeResponse>builder()
                .code(2000)
                .data(carTypeService.getById(typeId))
                .build();
    }

    @Operation(
            summary = "Cập nhật car type",
            description = """
                    Cập nhật car type theo id.
                    - Chỉ cập nhật các field được gửi lên
                    - Dùng cho admin
                    """
    )
    @PatchMapping("/{typeId}")
    public ApiCustomResponse<Void> update(
            @PathVariable UUID typeId,
            @RequestBody CarTypeRequest request
    ) {
        carTypeService.update(typeId, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Update car type successfully")
                .build();
    }

    @Operation(
            summary = "Xóa car type",
            description = """
                    Xóa car type theo id.
                    - Chỉ dùng cho admin
                    - Trả về lỗi nếu car type không tồn tại
                    """
    )
    @DeleteMapping("/{typeId}")
    public ApiCustomResponse<Void> delete(@PathVariable UUID typeId) {
        carTypeService.delete(typeId);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Delete car type successfully")
                .build();
    }
}
