package com.phuoc.carRental.mapper;

import com.phuoc.carRental.dto.requests.CarBrandRequest;
import com.phuoc.carRental.dto.responses.CarBrandResponse;
import com.phuoc.carRental.model.CarBrand;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CarBrandMapper {

    CarBrand toCarBrand(CarBrandRequest request);

    CarBrandResponse toCarBrandResponse(CarBrand brand);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCarBrand(@MappingTarget CarBrand brand, CarBrandRequest request);
}
