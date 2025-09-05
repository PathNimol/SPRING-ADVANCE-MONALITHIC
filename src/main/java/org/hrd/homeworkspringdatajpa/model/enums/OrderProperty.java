package org.hrd.homeworkspringdatajpa.model.enums;

import lombok.Getter;

@Getter
public enum OrderProperty {
    ID("id"),
    ORDER_DATE("orderDate"),
    TOTAL_AMOUNT("totalAmount");

    private final String fieldName;

    OrderProperty(String fieldName) {
        this.fieldName = fieldName;
    }

}