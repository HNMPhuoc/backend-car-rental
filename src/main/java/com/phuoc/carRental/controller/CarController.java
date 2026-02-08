package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.CarCreateRequest;
import com.phuoc.carRental.dto.requests.CarUpdateAttributesRequest;
import com.phuoc.carRental.dto.requests.CarUpdateMaintStatusRequest;
import com.phuoc.carRental.dto.requests.CarUpdateStatusRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.CarDetailResponse;
import com.phuoc.carRental.dto.responses.CarListResponse;
import com.phuoc.carRental.dto.responses.CarThumbnailResponse;
import com.phuoc.carRental.dto.responses.CarUserListResponse;
import com.phuoc.carRental.service.CarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Tag(name = "Car")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarController {

    CarService carService;

    @GetMapping("/{id}")
    public ApiCustomResponse<CarDetailResponse> detail(@PathVariable UUID id) {
        return ApiCustomResponse.<CarDetailResponse>builder()
                .code(2000)
                .data(carService.getDetail(id))
                .build();
    }

    @GetMapping("/{id}/thumbnail")
    public ApiCustomResponse<CarThumbnailResponse> getThumbnail(@PathVariable UUID id) {
        return ApiCustomResponse.<CarThumbnailResponse>builder()
                .code(2000)
                .data(carService.getThumbnail(id))
                .build();
    }

    @GetMapping("/admin/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiCustomResponse<Page<CarListResponse>> adminBrowse(
            @RequestParam(required = false) Boolean hide,
            @RequestParam(required = false) Boolean browseStatus,
            @RequestParam(required = false) UUID areaId,
            @RequestParam(required = false) UUID typeId,
            Pageable pageable
    ) {
        return ApiCustomResponse.<Page<CarListResponse>>builder()
                .code(2000)
                .data(carService.browse(hide, browseStatus, areaId, typeId, pageable))
                .build();
    }

    @GetMapping("/browse")
    public ApiCustomResponse<Page<CarListResponse>> userBrowse(
            @RequestParam(required = false) UUID areaId,
            @RequestParam(required = false) UUID typeId,
            Pageable pageable
    ) {
        return ApiCustomResponse.<Page<CarListResponse>>builder()
                .code(2000)
                .data(carService.getUserBrowse(areaId, typeId, pageable))
                .build();
    }

    @GetMapping("/employee/list")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiCustomResponse<Page<CarListResponse>> employeeBrowse(
            @RequestParam(required = false) Boolean hide,
            @RequestParam(required = false) Boolean browseStatus,
            @RequestParam(required = false) UUID areaId,
            @RequestParam(required = false) UUID typeId,
            Pageable pageable
    ) {
        return ApiCustomResponse.<Page<CarListResponse>>builder()
                .code(2000)
                .data(carService.employeeBrowse(hide, browseStatus, areaId, typeId, pageable))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ApiCustomResponse<Void> create(@RequestBody CarCreateRequest request, Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        carService.create(request, null);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Created")
                .build();
    }

    @GetMapping("/user/list")
    @PreAuthorize("hasRole('USER')")
    public ApiCustomResponse<Page<CarUserListResponse>> getUserCars(Authentication authentication, Pageable pageable) {
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        return ApiCustomResponse.<Page<CarUserListResponse>>builder()
                .code(2000)
                .data(carService.getUserCars(userId, pageable))
                .build();
    }

    @PatchMapping("/{id}/attributes")
    @PreAuthorize("hasRole('USER')")
    public ApiCustomResponse<Void> updateAttributes(
            @PathVariable UUID id,
            @RequestBody CarUpdateAttributesRequest request,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        carService.updateAttributes(id, userId, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Updated")
                .build();
    }

    @PostMapping("/{id}/upload-thumbnail")
    @PreAuthorize("hasRole('USER')")
    public ApiCustomResponse<Void> uploadThumbnail(
            @PathVariable UUID id,
            @RequestPart MultipartFile file,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        carService.updateThumbnail(id, userId, file);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Uploaded")
                .build();
    }

    @PatchMapping("/{id}/hide")
    @PreAuthorize("hasRole('USER')")
    public ApiCustomResponse<Void> updateHide(
            @PathVariable UUID id,
            @RequestBody CarUpdateStatusRequest request,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        carService.updateHideStatus(id, userId, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Updated")
                .build();
    }

    /**
     * User: Update car maintenance status
     */
    @PatchMapping("/{id}/maint-status")
    @PreAuthorize("hasRole('USER')")
    public ApiCustomResponse<Void> updateMaintStatus(
            @PathVariable UUID id,
            @RequestBody CarUpdateMaintStatusRequest request,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        carService.updateMaintStatus(id, userId, request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Updated")
                .build();
    }

    /**
     * User: Delete car
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ApiCustomResponse<Void> delete(@PathVariable UUID id, Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        carService.delete(id, userId);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Deleted")
                .build();
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiCustomResponse<Void> approveBrowse(@PathVariable UUID id) {
        carService.approveBrowseStatus(id);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Approved")
                .build();
    }
}
