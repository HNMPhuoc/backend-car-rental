package com.phuoc.carRental.common.enums;

import lombok.Getter;

@Getter
public enum DefaultValue {
    Avatar("http://localhost:8080/api/v1/users/default.png");
    private final String url;

    DefaultValue(String url) {
        this.url = url;
    }
}
