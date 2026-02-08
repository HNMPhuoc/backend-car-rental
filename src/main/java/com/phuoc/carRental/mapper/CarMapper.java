package com.phuoc.carRental.mapper;

import com.phuoc.carRental.dto.requests.CarCreateRequest;
import com.phuoc.carRental.dto.requests.CarUpdateAttributesRequest;
import com.phuoc.carRental.dto.requests.CarUpdateMaintStatusRequest;
import com.phuoc.carRental.dto.requests.CarUpdateStatusRequest;
import com.phuoc.carRental.dto.responses.CarDetailResponse;
import com.phuoc.carRental.dto.responses.CarListResponse;
import com.phuoc.carRental.dto.responses.CarThumbnailResponse;
import com.phuoc.carRental.dto.responses.CarUserListResponse;
import com.phuoc.carRental.model.Car;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car toCar(CarCreateRequest request);

    CarDetailResponse toCarDetail(Car car);

    CarThumbnailResponse toCarThumbnail(Car car);

    CarUserListResponse toCarUserList(Car car);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCarAttributes(@MappingTarget Car car, CarUpdateAttributesRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCarStatus(@MappingTarget Car car, CarUpdateStatusRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCarMaintStatus(@MappingTarget Car car, CarUpdateMaintStatusRequest request);
}
