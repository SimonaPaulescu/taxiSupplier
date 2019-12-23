package com.taxi.models.domain;

import com.taxi.models.json.TaxiInfo;

public class TaxiSearchResult implements Comparable<TaxiSearchResult> {
  private final TaxiInfo taxiInfo;
  private final String supplierId;

  public TaxiSearchResult(final TaxiInfo taxiInfo, final String supplierId) {
    this.taxiInfo = taxiInfo;
    this.supplierId = supplierId;
  }

  @Override
  public int compareTo(TaxiSearchResult o) {
    return this.getTaxiInfo().getPrice().compareTo(o.getTaxiInfo().getPrice());
  }

  public String getSupplierId() {
    return supplierId;
  }

  public TaxiInfo getTaxiInfo() {
    return taxiInfo;
  }
}
