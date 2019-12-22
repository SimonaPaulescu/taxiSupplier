package com.taxi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class TaxiService {
    private static List getTaxiResults(final String[] coordinates, Optional<Integer> numberOfPassengers) throws IOException, JSONException {
        HashMap<String, TaxiSearchResult> taxiSearchResults = new HashMap<>();
        final List<String> supplierIds = new ArrayList<>(Arrays.asList("dave", "jeff", "eric"));

        for (String supplierId : supplierIds) {
            final JSONObject result = sendGetRequest(supplierId, coordinates);
            if (result.length() > 0) {
                final ObjectMapper mapper = new ObjectMapper();
                final TaxiSuppliersResult taxiSupplierResult = mapper.readValue(result.toString(),
                        TaxiSuppliersResult.class);

                for (TaxiInfo currentTaxiInfo : taxiSupplierResult.getOptions()) {
                    if (taxiSearchResults.containsKey(currentTaxiInfo.getCarType())) {
                        final TaxiSearchResult taxiSearchResult = taxiSearchResults.get(currentTaxiInfo.getCarType());
                        if (taxiSearchResult.getTaxiInfo().getPrice() > currentTaxiInfo.getPrice()) {
                            taxiSearchResults.replace(currentTaxiInfo.getCarType(),
                                    new TaxiSearchResult(currentTaxiInfo, taxiSupplierResult.getSupplier()));
                        }
                    } else {
                        if (numberOfPassengers.isPresent() && CarCapacities.CARS.valueOf(currentTaxiInfo.getCarType()).getCapacity() >= numberOfPassengers.get()) {
                            taxiSearchResults.put(currentTaxiInfo.getCarType(), new TaxiSearchResult(currentTaxiInfo, taxiSupplierResult.getSupplier()));
                        }
                    }
                }
            }
        }
        return new ArrayList<>(taxiSearchResults.values());
    }

    private static JSONObject sendGetRequest(final String supplierId, final String[] coordinates) throws IOException, JSONException {
        URL url = new URL(String.format("https://techtest.rideways.com/%s?pickup=%s,%s&dropoff=%s,%s",
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
