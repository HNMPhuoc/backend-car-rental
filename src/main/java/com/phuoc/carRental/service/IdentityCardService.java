package com.phuoc.carRental.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.common.helpers.FileHelper;
import com.phuoc.carRental.dto.requests.IdentityCardRequest;
import com.phuoc.carRental.dto.responses.IdentityCardResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.IdentityCardMapper;
import com.phuoc.carRental.model.IdentityCard;
import com.phuoc.carRental.model.User;
import com.phuoc.carRental.repository.IdentityCardRepository;
import com.phuoc.carRental.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityCardService {

    IdentityCardRepository identityCardRepository;
    UserRepository userRepository;
    IdentityCardMapper mapper;

    String uploadDir;
    String pythonOcrUrl;

    ObjectMapper objectMapper;
    RestTemplate restTemplate;

    public IdentityCardService(
            IdentityCardRepository identityCardRepository,
            UserRepository userRepository,
            IdentityCardMapper mapper,
            ObjectMapper objectMapper, // Inject here
            @Value("${app.upload.dir}") String uploadDir,
            @Value("${app.python.ocr.url}") String pythonOcrUrl
    ) {
        this.identityCardRepository = identityCardRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper; // Assign injected bean
        this.uploadDir = uploadDir;
        this.pythonOcrUrl = pythonOcrUrl;
        this.restTemplate = new RestTemplate();
    }

    @Transactional
    public void create(IdentityCardRequest request) {

        if (identityCardRepository.existsByCccd(request.getCccd())) {
            throw new AppException(ErrorCode.IDENTITY_CARD_EXISTED);
        }

        if (identityCardRepository.existsByUser_Id(request.getUserId())) {
            throw new AppException(ErrorCode.USER_HAS_IDENTITY);
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        IdentityCard card = mapper.toIdentityCard(request);
        card.setUser(user);

        identityCardRepository.save(card);
    }

    @Transactional(readOnly = true)
    public List<IdentityCardResponse> getAll() {
        return identityCardRepository.findAllIdentityCard();
    }

    @Transactional(readOnly = true)
    public IdentityCardResponse getById(UUID id) {
        return identityCardRepository.findIdentityCardById(id)
                .orElseThrow(() -> new AppException(ErrorCode.IDENTITY_CARD_NOT_EXISTED));
    }

    @Transactional
    public void update(UUID id, IdentityCardRequest request) {

        IdentityCard card = identityCardRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.IDENTITY_CARD_NOT_EXISTED));

        if (request.getCccd() != null &&
                identityCardRepository.existsByCccdAndIdNot(request.getCccd(), id)) {
            throw new AppException(ErrorCode.IDENTITY_CARD_EXISTED);
        }

        mapper.updateIdentityCard(card, request);
    }

    @Transactional
    public void delete(UUID id) {
        if (!identityCardRepository.existsById(id)) {
            throw new AppException(ErrorCode.IDENTITY_CARD_NOT_EXISTED);
        }
        identityCardRepository.deleteById(id);
    }

    @Transactional
    public String uploadCccdImage(UUID id, MultipartFile file) {
        IdentityCard card = identityCardRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.IDENTITY_CARD_NOT_EXISTED));

        String filename = FileHelper.generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename()));
        String fullPath = uploadDir + "/" + filename;

        FileHelper.saveFile(file, fullPath);

        String imageUrl = "/uploads/" + filename;
        card.setUrlImage(imageUrl);

        return imageUrl;
    }

    @Transactional(readOnly = true)
    public String getCccdImageUrl(UUID id) {
        IdentityCard card = identityCardRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.IDENTITY_CARD_NOT_EXISTED));

        if (card.getUrlImage() == null) {
            throw new AppException(ErrorCode.IDENTITY_CARD_IMAGE_NOT_EXISTED);
        }

        return card.getUrlImage();
    }

    @Transactional
    public IdentityCardResponse createFromImageAndOcr(UUID userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (identityCardRepository.existsByUser_Id(userId)) {
            throw new AppException(ErrorCode.USER_HAS_IDENTITY);
        }

        String original = Objects.requireNonNull(file.getOriginalFilename());
        String filename = FileHelper.generateUniqueFileName(original);
        FileHelper.ensureDirectoryExists(uploadDir);
        String fullPath = uploadDir.endsWith("/") ? uploadDir + filename : uploadDir + "/" + filename;
        FileHelper.saveFile(file, fullPath);

        IdentityCardRequest ocrResult = callPythonOcr(file, filename);

        validateOcrResult(ocrResult);

        ocrResult.setUserId(userId);

        if (identityCardRepository.existsByCccd(ocrResult.getCccd())) {
            throw new AppException(ErrorCode.IDENTITY_CARD_EXISTED);
        }

        IdentityCard card = mapper.toIdentityCard(ocrResult);
        card.setUser(user);
        String imageUrl = "/uploads/" + filename;
        card.setUrlImage(imageUrl);

        IdentityCard saved = identityCardRepository.save(card);

        return mapper.toIdentityCardResponse(saved);
    }

    private void validateOcrResult(IdentityCardRequest ocr) {
        if (ocr == null) {
            throw new AppException(ErrorCode.OCR_EMPTY_RESULT);
        }

        if (ocr.getCccd() != null) {
            ocr.setCccd(ocr.getCccd().trim());
        }

        if (ocr.getCccd() == null || !ocr.getCccd().matches("\\d{12}")) {
//            log.error("Invalid CCCD format: '{}'", ocr.getCccd());
            throw new AppException(ErrorCode.INVALID_CCCD_FORMAT);
        }

        if (ocr.getFullName() == null || ocr.getFullName().isBlank()) {
            throw new AppException(ErrorCode.FULL_NAME_REQUIRED);
        }

        ocr.setFullName(ocr.getFullName().trim().toUpperCase());

        if (ocr.getDob() == null) {
            throw new AppException(ErrorCode.DOB_REQUIRED);
        }

        int age = Period.between(ocr.getDob(), LocalDate.now()).getYears();
        if (age < 18) {
            throw new AppException(ErrorCode.INVALID_DOB);
        }

        if (ocr.getGender() != null) {
            String gender = ocr.getGender().trim();
            if (!gender.equalsIgnoreCase("NAM") && !gender.equalsIgnoreCase("NỮ")) {
                throw new AppException(ErrorCode.INVALID_GENDER);
            }
            ocr.setGender(gender);
        }

        if (ocr.getNationally() != null && ocr.getNationally().length() > 50) {
            throw new AppException(ErrorCode.INVALID_NATIONALITY);
        }
    }


    private IdentityCardRequest callPythonOcr(MultipartFile file, String filename) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return filename;
                }
            };
            body.add("file", byteArrayResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

//            log.info("Calling Python OCR Service: {}", pythonOcrUrl);

            ResponseEntity<String> response = restTemplate.exchange(
                    pythonOcrUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

//            log.info("Python OCR Response Status: {}", response.getStatusCode());
//            log.info("Python OCR Raw Body: {}", response.getBody());

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                log.error("OCR Service failed with status {}", response.getStatusCode());
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }

            return objectMapper.readValue(response.getBody(), IdentityCardRequest.class);

        } catch (AppException ae) {
            throw ae;
        } catch (Exception ex) {
//            log.error("Error calling/parsing Python OCR", ex);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
