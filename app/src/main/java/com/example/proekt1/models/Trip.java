package com.example.proekt1.models;

public class Trip {
    private String tripId;
    private String from;
    private String to;
    private String driverName;
    private String vehicleModel;
    private String vehicleColor;
    private String driverId;
    private float rating;

    public Trip(String tripId, String from, String to, String driverName, String vehicleModel, String vehicleColor, String driverId, float rating) {
        this.tripId = tripId;
        this.from = from;
        this.to = to;
        this.driverName = driverName;
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
        this.driverId = driverId;
        this.rating = rating;
    }

    // Getters and Setters
    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getDriverName() { return driverName; }
    public String getVehicleModel() { return vehicleModel; }
    public String getVehicleColor() { return vehicleColor; }
    public String getDriverId() { return driverId; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
}
