package com.taxi;

public class TaxiSearchResult implements Comparable<TaxiSearchResult>{
    private TaxiInfo taxiInfo;
    private String supplierId;

    public String getSupplierId() {
        return supplierId;
    }

    public TaxiInfo getTaxiInfo() {
        return taxiInfo;
    }

    TaxiSearchResult(final TaxiInfo taxiInfo, final String supplierId) {
        this.taxiInfo = taxiInfo;
        this.supplierId = supplierId;
    }

    @Override
    public int compareTo(TaxiSearchResult o) {
        return this.getTaxiInfo().getPrice().compareTo(o.getTaxiInfo().getPrice());
    }
}
