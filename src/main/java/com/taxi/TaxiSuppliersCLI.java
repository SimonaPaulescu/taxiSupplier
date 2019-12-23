package com.taxi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.models.domain.TaxiSearchResult;
import com.taxi.helpers.CarCapacities;
import com.taxi.models.json.TaxiInfo;
import com.taxi.models.json.TaxiSuppliersResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.*;

public class TaxiSuppliersCLI {

  public static void main(String[] args) throws IOException, JSONException {
    Optional<Integer> numberOfPassengers = Optional.empty();
    if (args.length < 4) {
      throw new InvalidParameterException("Please supply 2 pairs of lat, lon coordinates");
    }

    for (String arg : args) {
      try {
        Float.parseFloat(arg);
      } catch (final NumberFormatException e) {
        e.printStackTrace();
      }
    }

    if (args.length > 4) {
      numberOfPassengers = Optional.of(Integer.parseInt(args[4]));
    }

    final List<TaxiSearchResult> results = getTaxiResults(args, numberOfPassengers);

    for (TaxiSearchResult result : results) {
      System.out.println(
          String.format(
              "%s -- %s -- %s ",
              result.getTaxiInfo().getCarType(),
              result.getSupplierId(),
              result.getTaxiInfo().getPrice()));
    }
  }

  private static List getTaxiResults(
      final String[] coordinates, Optional<Integer> numberOfPassengers)
      throws IOException, JSONException {
    HashMap<String, TaxiSearchResult> taxiSearchResults = new HashMap<>();
    final List<String> supplierIds = new ArrayList<>(Arrays.asList("dave", "jeff", "eric"));

    for (String supplierId : supplierIds) {
      final JSONObject result = sendGetRequest(supplierId, coordinates);
      if (result.length() > 0) {
        final ObjectMapper mapper = new ObjectMapper();
        final TaxiSuppliersResult taxiSupplierResult =
            mapper.readValue(result.toString(), TaxiSuppliersResult.class);

        for (TaxiInfo currentTaxiInfo : taxiSupplierResult.getOptions()) {
          if (taxiSearchResults.containsKey(currentTaxiInfo.getCarType())) {
            final TaxiSearchResult taxiSearchResult =
                taxiSearchResults.get(currentTaxiInfo.getCarType());
            if (taxiSearchResult.getTaxiInfo().getPrice() > currentTaxiInfo.getPrice()) {
              taxiSearchResults.replace(
                  currentTaxiInfo.getCarType(),
                  new TaxiSearchResult(currentTaxiInfo, taxiSupplierResult.getSupplier()));
            }
          } else {
            if (numberOfPassengers.isPresent()) {
              if (CarCapacities.CARS.valueOf(currentTaxiInfo.getCarType()).getCapacity()
                  >= numberOfPassengers.get()) {
                taxiSearchResults.put(
                    currentTaxiInfo.getCarType(),
                    new TaxiSearchResult(currentTaxiInfo, taxiSupplierResult.getSupplier()));
              }
            } else {
              taxiSearchResults.put(
                  currentTaxiInfo.getCarType(),
                  new TaxiSearchResult(currentTaxiInfo, taxiSupplierResult.getSupplier()));
            }
          }
        }
      }
    }
    final List<TaxiSearchResult> results = new ArrayList<>(taxiSearchResults.values());
    results.sort(Collections.reverseOrder());
    return results;
  }

  private static JSONObject sendGetRequest(final String supplierId, final String[] coordinates)
      throws IOException, JSONException {
    URL url =
        new URL(
            String.format(
                "https://techtest.rideways.com/%s?pickup=%s,%s&dropoff=%s,%s",
                supplierId, coordinates[0], coordinates[1], coordinates[2], coordinates[3]));
    final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setConnectTimeout(2000);
    final int responseCode = connection.getResponseCode();

    if (responseCode == HttpURLConnection.HTTP_OK) {
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      return new JSONObject(response.toString());
    } else {
      return new JSONObject();
    }
  }
}
