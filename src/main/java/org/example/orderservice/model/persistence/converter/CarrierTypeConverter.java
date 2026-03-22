package org.example.orderservice.model.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.orderservice.model.enums.CarrierType;

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
