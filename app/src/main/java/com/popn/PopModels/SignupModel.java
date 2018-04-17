package com.popn.PopModels;

/**
 * Created by Vs1 on 3/20/2018.
 */

public class SignupModel {
    String age,passcode,emailId,firstName,lastName,phoneNumber,networkName,colorCode,location;

    public SignupModel(String age, String passcode, String emailId, String firstName, String lastName,String location) {
        this.age = age;
        this.passcode = passcode;
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
    }

    public SignupModel() {
    }

    public SignupModel(String email, String passcode, String emailId, String firstName, String lastName, String phoneNumber, String networkName, String colorCode) {
        this.age = email;
        this.passcode = passcode;
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.networkName = networkName;
        this.colorCode = colorCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
