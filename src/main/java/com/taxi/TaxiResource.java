package com.taxi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaxiResource {

    @GetMapping("/taxi")
     float getTaxiOptions(@RequestParam("pickup_lat") final float pickupLat ,
                    @RequestParam("pickup_lon") final float pickupLon,
                     @RequestParam("dropoff_lat") final float dropOffLat ,
                     @RequestParam("dropoff_lon") final float dropOffLon,
                    @RequestParam(value="no_passengers", required = false) final Integer numberPassengers) {


            return 23.3f;
        }
    }

