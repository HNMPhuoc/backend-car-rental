package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.requests.AreaRequest;
import com.phuoc.carRental.dto.responses.AreaResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.AreaMapper;
import com.phuoc.carRental.model.Area;
import com.phuoc.carRental.repository.AreaRepository;
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
public class AreaService {
    AreaRepository areaRepository;
    AreaMapper areaMapper;

    public void create(AreaRequest request) {
        Area area = areaMapper.toArea(request);
        areaRepository.save(area);
    }

    @Transactional(readOnly = true)
    public List<AreaResponse> getAll() {
        return areaRepository.findAllArea();
    }

    @Transactional(readOnly = true)
    public AreaResponse getById(UUID areaId) {
        return areaRepository.findAreaById(areaId)
                .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_EXISTED));
    }

    @Transactional
    public void update(UUID areaId, AreaRequest request) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_EXISTED));
        areaMapper.updateArea(area, request);
    }

    public void delete(UUID areaId) {
        if (!areaRepository.existsById(areaId)) {
            throw new AppException(ErrorCode.AREA_NOT_EXISTED);
        }
        areaRepository.deleteById(areaId);
    }
}
