package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.requests.CarModelRequest;
import com.phuoc.carRental.dto.responses.CarModelResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.CarModelMapper;
import com.phuoc.carRental.model.CarBrand;
import com.phuoc.carRental.model.CarModel;
import com.phuoc.carRental.repository.CarBrandRepository;
import com.phuoc.carRental.repository.CarModelRepository;
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
public class CarModelService {

    CarModelRepository carModelRepository;
    CarBrandRepository carBrandRepository;
    CarModelMapper carModelMapper;

    public void create(CarModelRequest request) {

        if (!carBrandRepository.existsById(request.getBrandId())) {
            throw new AppException(ErrorCode.CAR_BRAND_NOT_EXISTED);
        }

        if (carModelRepository.existsByNameIgnoreCaseAndBrands_Id(
                request.getName(), request.getBrandId())) {
            throw new AppException(ErrorCode.CAR_MODEL_EXISTED);
        }

        CarBrand brand = carBrandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.CAR_BRAND_NOT_EXISTED));

        CarModel model = carModelMapper.toCarModel(request);
        model.setBrands(brand);

        carModelRepository.save(model);
    }

    @Transactional(readOnly = true)
    public List<CarModelResponse> getAll() {
        return carModelRepository.findAllCarModel();
    }

    @Transactional(readOnly = true)
    public CarModelResponse getById(UUID modelId) {
        return carModelRepository.findCarModelById(modelId)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_MODEL_NOT_EXISTED));
    }

    @Transactional
    public void update(UUID modelId, CarModelRequest request) {
        CarModel model = carModelRepository.findById(modelId)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_MODEL_NOT_EXISTED));
        if (request.getBrandId() != null) {
            if (!carBrandRepository.existsById(request.getBrandId())) {
                throw new AppException(ErrorCode.CAR_BRAND_NOT_EXISTED);
            }
            if (carModelRepository.existsByNameIgnoreCaseAndBrands_Id(
                    request.getName(), request.getBrandId())
                    && !(model.getName().equalsIgnoreCase(request.getName())
                    && model.getBrands().getId().equals(request.getBrandId()))) {

                throw new AppException(ErrorCode.CAR_MODEL_EXISTED);
            }
            CarBrand brand = carBrandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new AppException(ErrorCode.CAR_BRAND_NOT_EXISTED));

            model.setBrands(brand);
        }
        carModelMapper.updateCarModel(model, request);
    }


    public void delete(UUID modelId) {
        if (!carModelRepository.existsById(modelId)) {
            throw new AppException(ErrorCode.CAR_MODEL_NOT_EXISTED);
        }
        carModelRepository.deleteById(modelId);
    }
}
