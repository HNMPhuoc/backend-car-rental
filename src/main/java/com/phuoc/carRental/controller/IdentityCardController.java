package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.IdentityCardRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.IdentityCardResponse;
import com.phuoc.carRental.service.IdentityCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Identity Card",
        description = "Quản lý thông tin chứng minh nhân dân / căn cước"
)
@RestController
@RequestMapping("/api/v1/identity-cards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityCardController {
    IdentityCardService identityCardService;

    @Operation(summary = "Upload ảnh CCCD (OCR + create identity card)")
    @PostMapping(path = "/{userId}/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiCustomResponse<IdentityCardResponse> uploadImage(
            @PathVariable("userId") UUID userId,
            @RequestParam("file") MultipartFile file
    ) {
        IdentityCardResponse created = identityCardService.createFromImageAndOcr(userId, file);

        return ApiCustomResponse.<IdentityCardResponse>builder()
                .code(2000)
                .message("Upload & OCR success, identity card created")
                .data(created)
                .build();
    }

    @Operation(
            summary = "Lấy URL ảnh CCCD",
            description = """
                    Lấy URL ảnh CCCD đã upload.
                    - URL này có thể dùng trong frontend để hiển thị
                    """
    )
    @GetMapping("/{id}/image-url")
    public ApiCustomResponse<String> getImageUrl(
            @PathVariable UUID id
    ) {
        String imageUrl = identityCardService.getCccdImageUrl(id);

        return ApiCustomResponse.<String>builder()
                .code(2000)
                .message("Get CCCD image URL successfully")
                .data(imageUrl)
                .build();
    }

    @Operation(
            summary = "Xem ảnh CCCD",
            description = "Trả về file ảnh trực tiếp để hiển thị trên trình duyệt (img src)"
    )
    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> viewImage(@PathVariable UUID id) {

        Resource file = identityCardService.loadCccdImage(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    @Operation(
            summary = "Tạo mới Identity Card",
            description = """
                    Tạo thông tin CCCD mới cho người dùng.
                    - CCCD phải duy nhất
                    - Một user chỉ có 1 CCCD
                    """
    )
    @PostMapping
    public ApiCustomResponse<Void> create(
            @RequestBody @Valid IdentityCardRequest request
    ) {
        identityCardService.create(request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Create identity card successfully")
                .build();
    }

    @Operation(
            summary = "Lấy danh sách Identity Card",
            description = """
                    Lấy danh sách tất cả CCCD.
                    - Không phân trang
                    - Trả về thông tin cơ bản và userId
                    """
    )
    @GetMapping
    public ApiCustomResponse<List<IdentityCardResponse>> getAll() {
        return ApiCustomResponse.<List<IdentityCardResponse>>builder()
                .code(2000)
                .data(identityCardService.getAll())
                .build();
    }

    @Operation(
            summary = "Lấy thông tin Identity Card theo ID",
            description = """
                    Lấy chi tiết CCCD theo id.
                    - Trả về 404 nếu không tồn tại
                    """
    )
    @GetMapping("/{id}")
    public ApiCustomResponse<IdentityCardResponse> getById(
            @PathVariable UUID id
    ) {
        return ApiCustomResponse.<IdentityCardResponse>builder()
                .code(2000)
                .data(identityCardService.getById(id))
                .build();
    }

    @Operation(
            summary = "Cập nhật thông tin Identity Card",
            description = """
                    Cập nhật thông tin CCCD theo id.
                    - Chỉ cập nhật các field được gửi lên
                    - Kiểm tra duplicate cccd
                    """
    )
    @PatchMapping("/{id}")
    public ApiCustomResponse<Void> update(
            @PathVariable UUID id,
            @RequestBody @Valid IdentityCardRequest request
    ) {
        identityCardService.update(id, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Update identity card successfully")
                .build();
    }

    @Operation(
            summary = "Xóa Identity Card",
            description = """
                    Xóa thông tin CCCD theo id.
                    - Chỉ dùng khi CCCD tồn tại
                    """
    )
    @DeleteMapping("/{id}")
    public ApiCustomResponse<Void> delete(
            @PathVariable UUID id
    ) {
        identityCardService.delete(id);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Delete identity card successfully")
                .build();
    }
}
