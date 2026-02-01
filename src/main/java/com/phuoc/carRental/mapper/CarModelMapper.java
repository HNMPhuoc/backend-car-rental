package com.phuoc.carRental.mapper;

import com.phuoc.carRental.dto.requests.CarModelRequest;
import com.phuoc.carRental.dto.responses.CarModelResponse;
import com.phuoc.carRental.model.CarModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CarModelMapper {

    CarModel toCarModel(CarModelRequest request);

    @Mapping(source = "brands.id", target = "brandId")
    @Mapping(source = "brands.name", target = "brandName")
    CarModelResponse toCarModelResponse(CarModel model);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCarModel(@MappingTarget CarModel model, CarModelRequest request);
}
