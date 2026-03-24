package org.example.orderservice.model.enums;

public enum CarrierType {
    CDEK(1),
    YLDAM_EXPRESS(2);

    private final int code;

    CarrierType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CarrierType fromCode(Integer code) {
        if (code == null) {
            return null;
        }

        for (CarrierType value : values()) {
            if (value.code == code) {
                return value;
            }
        }

        throw new IllegalArgumentException("Unknown carrier type code: " + code);
    }
}
