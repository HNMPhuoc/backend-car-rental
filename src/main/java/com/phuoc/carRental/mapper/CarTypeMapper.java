package com.phuoc.carRental.mapper;

import com.phuoc.carRental.dto.requests.CarTypeRequest;
import com.phuoc.carRental.dto.responses.CarTypeResponse;
import com.phuoc.carRental.model.CarType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CarTypeMapper {

    CarType toCarType(CarTypeRequest request);

    @Mapping(source = "models.id", target = "modelId")
    @Mapping(source = "models.name", target = "modelName")
    CarTypeResponse toCarTypeResponse(CarType type);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCarType(@MappingTarget CarType type, CarTypeRequest request);
}
