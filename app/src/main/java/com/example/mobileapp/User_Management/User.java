package com.example.mobileapp.User_Management;

import java.io.Serializable;

public class User implements Serializable {
    private String ID;
    private String NIC;
    private String FirstName;
    private String LastName;
    private String UserName;
    private String Email;
    private String Password;
    private String RePassword;
    private String PhoneNumber;
    private String UserType;
    private String UserStatus;

    public User(String ID, String NIC, String FirstName, String LastName, String UserName, String Email, String Password, String RePassword, String PhoneNumber) {
        this.ID = ID;
        this.NIC = NIC;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.UserName = UserName;
        this.Email = Email;
        this.Password = Password;
        this.RePassword = RePassword;
        this.PhoneNumber = PhoneNumber;
        this.UserType = "Traveller";
        this.UserStatus = "Active";
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {return LastName;}

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getUserName() {return UserName;}

    public void setUserName(String username) {this.UserName = username;}

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {this.Email = email;}

    public String getPassword() {return Password;}

    public void setPassword(String password) {this.Password = password;}

    public String getRePassword() {return RePassword;}

    public void setRePassword(String reenteredPassword) {this.RePassword = reenteredPassword;}

    public String getPhoneNumber() {return PhoneNumber;}

    public void setPhoneNumber(String contactNumber) {this.PhoneNumber = contactNumber;}

    public String getUserType() {return UserType;}

    public void setUserType(String userType) {this.UserType = userType;}

    public String getUserStatus() {return UserStatus;}

    public void setUserStatus(String userStatus) {this.UserStatus = userStatus;}
}