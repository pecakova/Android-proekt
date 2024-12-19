package com.example.proekt1.models;

public class Driver {
    private final String userId;
    private final String name;
    private final String vehicleModel;
    private final String vehicleColor;

    // Constructor
    public Driver(String userId, String name, String vehicleModel, String vehicleColor) {
        this.userId = userId;
        this.name = name;
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }
}
