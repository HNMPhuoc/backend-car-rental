package com.phuoc.carRental.mapper;

import com.phuoc.carRental.common.enums.DefaultValue;
import com.phuoc.carRental.dto.requests.UserAddRequest;
import com.phuoc.carRental.dto.requests.UserEditRequest;
import com.phuoc.carRental.dto.responses.UserListResponse;
import com.phuoc.carRental.model.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lock", constant = "false")
    @Mapping(target = "online", constant = "false")
    @Mapping(target = "avatar", expression = "java(defaultAvatar())")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "driLicense", ignore = true)
    User toCreateEntity(UserAddRequest req);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateEntity(@MappingTarget User user, UserEditRequest req);

    UserListResponse toUserListResponse(User user);

    default String defaultAvatar() {
        return DefaultValue.Avatar.getUrl();
    }
}
