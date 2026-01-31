package com.phuoc.carRental.mapper;

import com.phuoc.carRental.dto.requests.AreaRequest;
import com.phuoc.carRental.dto.responses.AreaResponse;
import com.phuoc.carRental.model.Area;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AreaMapper {
    Area toArea(AreaRequest request);

    AreaResponse toAreaResponse(Area area);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArea(@MappingTarget Area area, AreaRequest request);
}
