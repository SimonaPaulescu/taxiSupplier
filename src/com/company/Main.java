package com.taxi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
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
        results.sort(Collections.reverseOrder());
        for (TaxiSearchResult result : results) {
            System.out.println(String.format("%s -- %s -- %s ", result.getTaxiInfo().getCarType(), result.getSupplierId(), result.getTaxiInfo().getPrice()));
        }
    }

    private static List getTaxiResults(final String[] parameters, Optional<Integer> numberOfPassengers) throws IOException {
        HashMap<String, TaxiSearchResult> taxiSearchResults = new HashMap<>();
        final List<String> supplierIds = new ArrayList<>(Arrays.asList("dave", "jeff", "eric"));

        for (String supplierId : supplierIds) {
            final JSONObject result = sendGetRequest(supplierId, parameters);
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

    private static JSONObject sendGetRequest(final String supplierId, final String[] coordinates) throws IOException {
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
