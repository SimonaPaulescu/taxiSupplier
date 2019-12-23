package com.taxi.resource;

import com.taxi.models.json.TaxiSearchResult;
import com.taxi.service.TaxiService;
import com.taxi.transformer.TaxiSearchResultTransformer;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class TaxiResource {
  private final TaxiService taxiService;
  private final TaxiSearchResultTransformer taxiSearchResultTransformer;

  @Autowired
  public TaxiResource(
      final TaxiService taxiService,
      final TaxiSearchResultTransformer taxiSearchResultTransformer) {
    this.taxiService = taxiService;
    this.taxiSearchResultTransformer = taxiSearchResultTransformer;
  }

  @GetMapping("/taxi")
  List<TaxiSearchResult> getTaxiOptions(
      @RequestParam("pickup_lat") final float pickupLat,
      @RequestParam("pickup_lon") final float pickupLon,
      @RequestParam("dropoff_lat") final float dropOffLat,
      @RequestParam("dropoff_lon") final float dropOffLon,
      @RequestParam(value = "no_passengers", required = false) final Integer numberPassengers)
      throws IOException, JSONException {
    Optional<Integer> passengers =
        numberPassengers != null ? Optional.of(numberPassengers) : Optional.empty();

    return taxiSearchResultTransformer.domainListToJson(
        taxiService.getTaxiResults(pickupLat, pickupLon, dropOffLat, dropOffLon, passengers));
  }
}
