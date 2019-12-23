package com.taxi.helpers;

public class CarCapacities {

  public enum CARS {
    STANDARD(4),
    EXECUTIVE(4),
    LUXURY(4),
    PEOPLE_CARRIER(6),
    LUXURY_PEOPLE_CARRIER(6),
    MINIBUS(16);

    private int maximumCapacity;

    CARS(int capacity) {
      this.maximumCapacity = capacity;
    }

    public int getCapacity() {
      return this.maximumCapacity;
    }
  }
}
