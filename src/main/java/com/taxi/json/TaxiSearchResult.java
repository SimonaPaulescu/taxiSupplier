package com.taxi.json;

public class TaxiSearchResult implements Comparable<TaxiSearchResult> {
  private TaxiInfo taxiInfo;
  private String supplierId;

  public TaxiSearchResult(final TaxiInfo taxiInfo, final String supplierId) {
    this.taxiInfo = taxiInfo;
    this.supplierId = supplierId;
  }

  public TaxiSearchResult() {}

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
