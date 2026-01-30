package com.phuoc.carRental.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @NotBlank(message = "ROLE_NAME_REQUIRED")
    @Size(min = 3, max = 50, message = "INVALID_ROLE_NAME")
    String name;

    @Size(max = 255, message = "DESCRIPTION_TOO_LONG")
    String description;

    Set<UUID> permissions;
}
