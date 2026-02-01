package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.requests.CarBrandRequest;
import com.phuoc.carRental.dto.responses.CarBrandResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.CarBrandMapper;
import com.phuoc.carRental.model.CarBrand;
import com.phuoc.carRental.repository.CarBrandRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarBrandService {

    CarBrandRepository carBrandRepository;
    CarBrandMapper carBrandMapper;

    public void create(CarBrandRequest request) {

        if (carBrandRepository.existsByNameIgnoreCase(request.getName())) {
            throw new AppException(ErrorCode.CAR_BRAND_EXISTED);
        }

        CarBrand brand = carBrandMapper.toCarBrand(request);
        carBrandRepository.save(brand);
    }


    @Transactional(readOnly = true)
    public List<CarBrandResponse> getAll() {
        return carBrandRepository.findAllCarBrand();
    }

    @Transactional(readOnly = true)
    public CarBrandResponse getById(UUID brandId) {
        return carBrandRepository.findCarBrandById(brandId)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_BRAND_NOT_EXISTED));
    }

    @Transactional
    public void update(UUID brandId, CarBrandRequest request) {
        CarBrand brand = carBrandRepository.findById(brandId)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_BRAND_NOT_EXISTED));
        carBrandMapper.updateCarBrand(brand, request);
    }

    public void delete(UUID brandId) {
        if (!carBrandRepository.existsById(brandId)) {
            throw new AppException(ErrorCode.CAR_BRAND_NOT_EXISTED);
        }
        carBrandRepository.deleteById(brandId);
    }
}
