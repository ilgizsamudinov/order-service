package org.example.orderservice.mapper;

import org.example.orderservice.dto.user.UserRequest;
import org.example.orderservice.dto.user.UserResponse;
import org.example.orderservice.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest userRequest);
    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void merge(@MappingTarget User existing, User user);
}
