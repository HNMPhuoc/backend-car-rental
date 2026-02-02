package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.requests.CarTypeRequest;
import com.phuoc.carRental.dto.responses.CarTypeResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.CarTypeMapper;
import com.phuoc.carRental.model.CarModel;
import com.phuoc.carRental.model.CarType;
import com.phuoc.carRental.repository.CarModelRepository;
import com.phuoc.carRental.repository.CarTypeRepository;
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
public class CarTypeService {

    CarTypeRepository carTypeRepository;
    CarModelRepository carModelRepository;
    CarTypeMapper carTypeMapper;

    @Transactional
    public void create(CarTypeRequest request) {

        if (carTypeRepository.existsByNameIgnoreCaseAndModels_Id(
                request.getName(), request.getModelId()
        )) {
            throw new AppException(ErrorCode.CAR_TYPE_EXISTED);
        }

        CarModel model = carModelRepository.findById(request.getModelId())
                .orElseThrow(() -> new AppException(ErrorCode.CAR_MODEL_NOT_EXISTED));

        CarType type = carTypeMapper.toCarType(request);
        type.setModels(model);

        carTypeRepository.save(type);
    }

    @Transactional(readOnly = true)
    public List<CarTypeResponse> getAll() {
        return carTypeRepository.findAllCarType();
    }

    @Transactional(readOnly = true)
    public CarTypeResponse getById(UUID typeId) {
        return carTypeRepository.findCarTypeById(typeId)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_TYPE_NOT_EXISTED));
    }

    @Transactional
    public void update(UUID typeId, CarTypeRequest request) {

        CarType type = carTypeRepository.findById(typeId)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_TYPE_NOT_EXISTED));
        CarModel model = type.getModels();
        if (request.getModelId() != null
                && !request.getModelId().equals(model.getId())) {

            model = carModelRepository.findById(request.getModelId())
                    .orElseThrow(() -> new AppException(ErrorCode.CAR_MODEL_NOT_EXISTED));
        }
        String newName = request.getName() != null
                ? request.getName()
                : type.getName();

        boolean nameChanged = request.getName() != null
                && !request.getName().equalsIgnoreCase(type.getName());

        boolean modelChanged = request.getModelId() != null
                && !model.getId().equals(type.getModels().getId());

        if ((nameChanged || modelChanged)
                && carTypeRepository.existsByNameIgnoreCaseAndModels_IdAndIdNot(
                newName, model.getId(), typeId)) {

            throw new AppException(ErrorCode.CAR_TYPE_EXISTED);
        }

        type.setModels(model);
        carTypeMapper.updateCarType(type, request);
    }

    public void delete(UUID typeId) {
        if (!carTypeRepository.existsById(typeId)) {
            throw new AppException(ErrorCode.CAR_TYPE_NOT_EXISTED);
        }
        carTypeRepository.deleteById(typeId);
    }
}
