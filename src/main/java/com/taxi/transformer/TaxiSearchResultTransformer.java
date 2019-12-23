package com.taxi.transformer;

import com.taxi.json.TaxiSearchResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaxiSearchResultTransformer {

  public TaxiSearchResult toJson(final com.taxi.domain.TaxiSearchResult domain) {
    return new TaxiSearchResult(domain.getTaxiInfo(), domain.getSupplierId());
  }

  public List<TaxiSearchResult> domainListToJson(
      final List<com.taxi.domain.TaxiSearchResult> domainList) {
    List<TaxiSearchResult> jsonList = new ArrayList<>();
    for (com.taxi.domain.TaxiSearchResult domain : domainList) {
      TaxiSearchResult json = toJson(domain);
      jsonList.add(json);
    }
    return jsonList;
  }

  public TaxiSearchResult toDomain(final TaxiSearchResult json) {
    return new TaxiSearchResult(json.getTaxiInfo(), json.getSupplierId());
  }
}
