package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.requests.PaymentMethodRequest;
import com.phuoc.carRental.dto.responses.PaymentMethodResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.PaymentMethodMapper;
import com.phuoc.carRental.model.PaymentMethod;
import com.phuoc.carRental.repository.PaymentMethodRepository;
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
public class PaymentMethodService {

    PaymentMethodRepository paymentMethodRepository;
    PaymentMethodMapper mapper;

    @Transactional
    public void create(PaymentMethodRequest request) {
        PaymentMethod method = mapper.toPaymentMethod(request);
        paymentMethodRepository.save(method);
    }

    @Transactional(readOnly = true)
    public List<PaymentMethodResponse> getAll() {
        return paymentMethodRepository.findAllPaymentMethod();
    }

    @Transactional(readOnly = true)
    public PaymentMethodResponse getById(UUID id) {
        return paymentMethodRepository.findPaymentMethodById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED));
    }

    @Transactional
    public void update(UUID id, PaymentMethodRequest request) {
        PaymentMethod method = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED));

        mapper.updatePaymentMethod(method, request);
    }

    @Transactional
    public void delete(UUID id) {
        if (!paymentMethodRepository.existsById(id)) {
            throw new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED);
        }
        paymentMethodRepository.deleteById(id);
    }
}
