package com.example.mobileapp.Train_Reservation_Management;

import java.io.Serializable;

public class TrainReservation implements Serializable {
    private String ID;
    private String travelerName;
    private String NIC;
    private String userId;
    private String trainID;
    private String bookingStatus;
    private String departureLocation;
    private String destinationLocation;
    private int numPassengers;
    private int age;
    private String ticketClass;
    private String seatSelection;
    private String email;
    private String phone;
    private String reservationDate;
    private String bookingDate;
    private String formattedReservationDate;
    private String formattedBookingDate;

    public TrainReservation(String ID, String travelerName, String nic, String userID, String trainID, String active, String departureLocation, String destinationLocation, int numPassengers, int age, String ticketClass, String seatSelection, String email, String phone, String reservationDate, String bookingDate, String formattedReservationDate, String formattedBookingDate) {
        this.ID = ID;
        this.travelerName = travelerName;
        this.NIC = nic;
        this.userId = userID;
        this.trainID = trainID;
        this.bookingStatus = active;
        this.departureLocation = departureLocation;
        this.destinationLocation = destinationLocation;
        this.numPassengers = numPassengers;
        this.age = age;
        this.ticketClass = ticketClass;
        this.seatSelection = seatSelection;
        this.email = email;
        this.phone = phone;
        this.reservationDate = reservationDate;
        this.bookingDate = bookingDate;
        this.formattedReservationDate = formattedReservationDate;
        this.formattedBookingDate = formattedBookingDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getFormattedReservationDate() {
        return formattedReservationDate;
    }

    public void setFormattedReservationDate(String formattedReservationDate) {
        this.formattedReservationDate = formattedReservationDate;
    }

    public String getFormattedBookingDate() {
        return formattedBookingDate;
    }

    public void setFormattedBookingDate(String formattedBookingDate) {
        this.formattedBookingDate = formattedBookingDate;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrainID() {
        return trainID;
    }

    public void setTrainID(String trainID) {
        this.trainID = trainID;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    public String getSeatSelection() {
        return seatSelection;
    }

    public void setSeatSelection(String seatSelection) {
        this.seatSelection = seatSelection;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}