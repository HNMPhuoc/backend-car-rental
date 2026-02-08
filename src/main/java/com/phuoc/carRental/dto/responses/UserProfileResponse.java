package com.phuoc.carRental.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    UUID id;
    String email;
    String username;
    String phoneNum;
    String avatar;
    Set<String> roles;
    Set<String> permissions;
}
