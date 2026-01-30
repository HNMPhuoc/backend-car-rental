package com.phuoc.carRental.mapper;

import com.phuoc.carRental.dto.requests.RoleRequest;
import com.phuoc.carRental.dto.responses.RoleResponse;
import com.phuoc.carRental.model.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateRole(@MappingTarget Role role, RoleRequest request);
}
