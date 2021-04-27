package com.stock.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

public enum ConditionType {
    DELTA_LARGER_THAN;

    private static Map<String, ConditionType> mapping = new HashMap<>();

    static {
        mapping.put("delta-larger-than", DELTA_LARGER_THAN);
        mapping.put(DELTA_LARGER_THAN.name(), DELTA_LARGER_THAN);
        // ...
    }

    @JsonCreator
    public static ConditionType fromString(String value) {
        return mapping.get(value);
    }
}
