package com.taxi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaxiInfo {
    @JsonProperty("car_type")
    private String carType;
    private Long price;

    public TaxiInfo() {

    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public TaxiInfo(final String carType, final Long price) {
        this.carType = carType;
        this.price = price;
    }
}
