package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.RoleRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.RoleResponse;
import com.phuoc.carRental.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/roles")
@Tag(
        name = "Role Controller",
        description = "API quản lý Role và Permission dành cho Admin"
)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @Operation(
            summary = "Tạo mới role",
            description = "Tạo role mới và gán danh sách permission cho role (Admin only)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tạo role thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu request không hợp lệ"),
            @ApiResponse(responseCode = "409", description = "Role đã tồn tại")
    })
    @PostMapping
    public ApiCustomResponse<Void> create(@RequestBody @Valid RoleRequest request) {
        roleService.create(request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Create role successfully")
                .build();
    }

    @Operation(
            summary = "Lấy danh sách role",
            description = "Trả về danh sách tất cả role kèm permission"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách role thành công")
    })
    @GetMapping
    public ApiCustomResponse<List<RoleResponse>> getAll() {
        return ApiCustomResponse.<List<RoleResponse>>builder()
                .code(2000)
                .data(roleService.getAll())
                .build();
    }

    @Operation(
            summary = "Lấy chi tiết role",
            description = "Lấy thông tin chi tiết của role theo roleId"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy chi tiết role thành công"),
            @ApiResponse(responseCode = "404", description = "Role không tồn tại")
    })
    @GetMapping("/{roleId}")
    public ApiCustomResponse<RoleResponse> getDetail(@PathVariable UUID roleId) {
        return ApiCustomResponse.<RoleResponse>builder()
                .code(2000)
                .data(roleService.getById(roleId))
                .build();
    }

    @Operation(
            summary = "Cập nhật role",
            description = "Cập nhật thông tin role và danh sách permission"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cập nhật role thành công"),
            @ApiResponse(responseCode = "404", description = "Role không tồn tại"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu request không hợp lệ")
    })
    @PatchMapping("/{roleId}")
    public ApiCustomResponse<Void> update(
            @PathVariable UUID roleId,
            @RequestBody @Valid RoleRequest request
    ) {
        roleService.update(roleId, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Update role successfully")
                .build();
    }

    @Operation(
            summary = "Xóa role",
            description = "Xóa role theo roleId (Admin only)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Xóa role thành công"),
            @ApiResponse(responseCode = "404", description = "Role không tồn tại")
    })
    @DeleteMapping("/{roleId}")
    public ApiCustomResponse<Void> delete(@PathVariable UUID roleId) {
        roleService.delete(roleId);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Delete role successfully")
                .build();
    }
}
