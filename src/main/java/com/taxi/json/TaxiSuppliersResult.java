package com.taxi.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TaxiSuppliersResult {
  @JsonProperty("supplier_id")
  private String supplier;

  private String pickup;

  @JsonProperty("dropoff")
  private String dropOff;

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getPickup() {
    return pickup;
  }

  public void setPickup(String pickup) {
    this.pickup = pickup;
  }

  public String getDropOff() {
    return dropOff;
  }

  public void setDropOff(String dropOff) {
    this.dropOff = dropOff;
  }

  public List<TaxiInfo> getOptions() {
    return options;
  }

  public void setOptions(List<TaxiInfo> options) {
    this.options = options;
  }

  private List<TaxiInfo> options;

  public TaxiSuppliersResult() {}

  public TaxiSuppliersResult(
      final String supplier,
      final String pickup,
      final String dropOff,
      final List<TaxiInfo> options) {
    this.supplier = supplier;
    this.pickup = pickup;
    this.dropOff = dropOff;
    this.options = options;
  }
}
