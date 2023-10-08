package com.example.mobileapp.Train_Management;

public class Train {
    private String trainID;
    private String trainName;
    private String departureTime;
    private String arrivalTime;
    private String status;

    public Train(String trainID, String trainName, String departureTime, String arrivalTime, String status) {
        this.trainID = trainID;
        this.trainName = trainName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
    }

    public String getTrainID() {
        return trainID;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getDTime() {
        return departureTime;
    }

    public String getATime() {
        return arrivalTime;
    }

    public String getStatus() {
        return status;
    }
}