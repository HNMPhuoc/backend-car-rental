package com.phuoc.carRental.mapper;

import com.phuoc.carRental.dto.requests.PaymentMethodRequest;
import com.phuoc.carRental.dto.responses.PaymentMethodResponse;
import com.phuoc.carRental.model.PaymentMethod;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethod toPaymentMethod(PaymentMethodRequest request);

    PaymentMethodResponse toPaymentMethodResponse(PaymentMethod method);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePaymentMethod(
            @MappingTarget PaymentMethod method,
            PaymentMethodRequest request
    );
}
