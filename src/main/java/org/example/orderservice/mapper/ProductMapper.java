package org.example.orderservice.mapper;

import org.example.orderservice.dto.product.ProductRequest;
import org.example.orderservice.dto.product.ProductResponse;
import org.example.orderservice.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequest productRequest);

    ProductResponse toResponse(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    void merge(@MappingTarget Product existing, Product product);



}