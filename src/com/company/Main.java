package com.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

public class Main {

    public static void main(String[] args) throws IOException {
        for (String arg : args) {
            System.out.println(arg);
        }
        System.out.println(args.length);
        if (args.length < 4) {
            throw new InvalidParameterException("Please supply 2 pairs of lat, lon coordinates");
        }

        try {
            final float pickupLat = Float.parseFloat(args[0]);
            final float pickupLon = Float.parseFloat(args[1]);
            final float dropLat = Float.parseFloat(args[2]);
            final float dropLon = Float.parseFloat(args[3]);
            final String result = getTaxiResults(pickupLat, pickupLon, dropLat, dropLon);
            System.out.println(result);
        } catch (final NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static String getTaxiResults(float lat1, float lon1, float lat2, float lon2) throws IOException {
        URL url = new URL(String.format("https://techtest.rideways.com/dave?pickup=%s,%s&dropoff=%s,%s", lat1, lon1, lat2, lon2));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        System.out.println("Request returned with code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new InternalError("The request is unsuccessful");
        }
    }
}
