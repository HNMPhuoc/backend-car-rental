package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.common.helpers.FileHelper;
import com.phuoc.carRental.dto.requests.CarCreateRequest;
import com.phuoc.carRental.dto.requests.CarUpdateAttributesRequest;
import com.phuoc.carRental.dto.requests.CarUpdateMaintStatusRequest;
import com.phuoc.carRental.dto.requests.CarUpdateStatusRequest;
import com.phuoc.carRental.dto.responses.CarDetailResponse;
import com.phuoc.carRental.dto.responses.CarListResponse;
import com.phuoc.carRental.dto.responses.CarThumbnailResponse;
import com.phuoc.carRental.dto.responses.CarUserListResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.CarMapper;
import com.phuoc.carRental.model.Car;
import com.phuoc.carRental.model.User;
import com.phuoc.carRental.repository.AreaRepository;
import com.phuoc.carRental.repository.CarRepository;
import com.phuoc.carRental.repository.CarTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarService {

    final CarRepository carRepository;
    final AreaRepository areaRepository;
    final CarTypeRepository carTypeRepository;
    final CarMapper mapper;

    @Value("${app.upload.dir}")
    String uploadDir;

    @Transactional
    public void create(CarCreateRequest request, User user) {
        Car car = mapper.toCar(request);
        car.setAreas(areaRepository.getReferenceById(request.getAreaId()));
        car.setType(carTypeRepository.getReferenceById(request.getTypeId()));
        car.setUser(user);
        car.setBrowseStatus(false); // Default: pending approval
        car.setHide(false); // Default: visible
        car.setMaintStatus(false); // Default: maintenance not needed
        carRepository.save(car);
    }

    @Transactional
    public void updateAttributes(UUID id, UUID userId, CarUpdateAttributesRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_EXISTED));

        // Verify ownership
        if (!car.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        mapper.updateCarAttributes(car, request);
        carRepository.save(car);
    }

    @Transactional
    public void updateThumbnail(UUID id, UUID userId, MultipartFile file) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_EXISTED));

        // Verify ownership
        if (!car.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        FileHelper.ensureDirectoryExists(uploadDir);
        String name = FileHelper.generateUniqueFileName(file.getOriginalFilename());
        FileHelper.saveFile(file, uploadDir + "/" + name);

        car.setThumbnail("/uploads/" + name);
        carRepository.save(car);
    }

    @Transactional
    public void updateHideStatus(UUID id, UUID userId, CarUpdateStatusRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_EXISTED));

        // Verify ownership
        if (!car.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        carRepository.updateHideStatus(id, request.isHide());
    }

    @Transactional
    public void updateMaintStatus(UUID id, UUID userId, CarUpdateMaintStatusRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_EXISTED));

        // Verify ownership
        if (!car.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        carRepository.updateMaintStatus(id, request.isMaintStatus());
    }

    @Transactional
    public void delete(UUID id, UUID userId) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_EXISTED));

        if (!car.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        carRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<CarUserListResponse> getUserCars(UUID userId, Pageable pageable) {
        return carRepository.getUserCars(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CarListResponse> employeeBrowse(
            Boolean hide,
            Boolean browseStatus,
            UUID areaId,
            UUID typeId,
            Pageable pageable
    ) {
        return carRepository.filterCars(hide, browseStatus, areaId, typeId, pageable);
    }

    @Transactional
    public void approveBrowseStatus(UUID id) {
        if (!carRepository.existsById(id)) {
            throw new AppException(ErrorCode.CAR_NOT_EXISTED);
        }
        carRepository.approveCar(id);
    }


    @Transactional(readOnly = true)
    public CarDetailResponse getDetail(UUID id) {
        return mapper.toCarDetail(
                carRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_EXISTED))
        );
    }

    @Transactional(readOnly = true)
    public CarThumbnailResponse getThumbnail(UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_EXISTED));
        return mapper.toCarThumbnail(car);
    }

    @Transactional(readOnly = true)
    public Page<CarListResponse> browse(
            Boolean hide,
            Boolean browseStatus,
            UUID areaId,
            UUID typeId,
            Pageable pageable
    ) {
        return carRepository.filterCars(hide, browseStatus, areaId, typeId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CarListResponse> getUserBrowse(UUID areaId, UUID typeId, Pageable pageable) {
        return carRepository.getUserBrowsableCars(areaId, typeId, pageable);
    }
}
