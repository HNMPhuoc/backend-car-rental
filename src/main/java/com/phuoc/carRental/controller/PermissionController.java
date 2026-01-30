package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.PermissionRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.PermissionResponse;
import com.phuoc.carRental.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/permissions")
@Tag(
        name = "Permission Controller",
        description = "Quản lý Permission dành cho Admin"
)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @Operation(
            summary = "Tạo permission mới và gán danh sách role",
            description = """
                    Tạo mới một permission.
                    - Dùng cho admin
                    - Name phải là duy nhất
                    """
    )
    @PostMapping
    public ApiCustomResponse<Void> create(@RequestBody PermissionRequest request) {
        permissionService.create(request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Create permission successfully")
                .build();
    }

    @Operation(
            summary = "Lấy danh sách permission",
            description = """
                    Lấy danh sách tất cả permission.
                    - Không phân trang
                    - Dùng cho màn hình quản trị
                    """
    )
    @GetMapping
    public ApiCustomResponse<List<PermissionResponse>> getAll() {
        return ApiCustomResponse.<List<PermissionResponse>>builder()
                .code(2000)
                .data(permissionService.getAll())
                .build();
    }

    @Operation(
            summary = "Lấy permission theo ID",
            description = """
                    Lấy chi tiết permission theo id.
                    - Dùng khi admin mở màn hình edit / view
                    - Trả về 404 nếu không tồn tại
                    """
    )
    @GetMapping("/{permissionId}")
    public ApiCustomResponse<PermissionResponse> getDetail(@PathVariable UUID permissionId) {
        return ApiCustomResponse.<PermissionResponse>builder()
                .code(2000)
                .data(permissionService.getById(permissionId))
                .build();
    }

    @Operation(
            summary = "Cập nhật permission",
            description = """
                    Cập nhật permission theo id.
                    - Chỉ cập nhật các field được gửi lên
                    - Dùng cho admin
                    """
    )
    @PatchMapping("/{permissionId}")
    public ApiCustomResponse<Void> update(
            @PathVariable UUID permissionId,
            @RequestBody PermissionRequest request
    ) {
        permissionService.update(permissionId, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Update permission successfully")
                .build();
    }


    @Operation(
            summary = "Xóa permission",
            description = """
                    Xóa permission theo id.
                    - Chỉ dùng cho admin
                    - Trả về lỗi nếu permission không tồn tại
                    """
    )
    @DeleteMapping("/{permissionId}")
    public ApiCustomResponse<Void> delete(@PathVariable UUID permissionId) {
        permissionService.delete(permissionId);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Delete successfully")
                .build();
    }
}
