package org.example.orderservice.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CarrierTypeConverter implements AttributeConverter<CarrierType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CarrierType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public CarrierType convertToEntityAttribute(Integer dbData) {
        return CarrierType.fromCode(dbData);
    }
}
