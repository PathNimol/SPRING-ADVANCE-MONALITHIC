package org.hrd.homeworkspringdatajpa.model.enums;

import lombok.Getter;

@Getter
public enum ProductProperty {
    ID("id"),
    NAME("name"),
    UNIT_PRICE("unitPrice"); // maps to entity field

    private final String fieldName;

    ProductProperty(String fieldName) {
        this.fieldName = fieldName;
    }

}
