package com.phuoc.carRental.mapper;

import com.phuoc.carRental.dto.requests.IdentityCardRequest;
import com.phuoc.carRental.dto.responses.IdentityCardResponse;
import com.phuoc.carRental.model.IdentityCard;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IdentityCardMapper {

    @Mapping(target = "urlImage", ignore = true)
    IdentityCard toIdentityCard(IdentityCardRequest request);

    @Mapping(source = "user.id", target = "userId")
    IdentityCardResponse toIdentityCardResponse(IdentityCard card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateIdentityCard(@MappingTarget IdentityCard card, IdentityCardRequest request);
}
