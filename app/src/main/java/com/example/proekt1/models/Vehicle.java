package com.example.proekt1.models;

public class Vehicle {
    private String model;
    private String year;
    private String color;
    private String plate;
    private String availability;
    private String driverId;

    // Empty constructor for Firestore
    public Vehicle() {}

    public Vehicle(String model, String year, String color, String plate, String availability, String driverId) {
        this.model = model;
        this.year = year;
        this.color = color;
        this.plate = plate;
        this.availability = availability;
        this.driverId = driverId;
    }

    // Getters and setters
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
}
