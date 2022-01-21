package com.bmsce.clique_shopwithfriends;

public class UserDefinition {

    String name, email, phoneNo, city, backupEmail, backupPhoneNo;

    public UserDefinition() {
    }

    public UserDefinition(String name, String email) {
        this.name = name;
        this.email = email;
        this.phoneNo = "";
        this.city = "";
        this.backupEmail = "";
        this.backupPhoneNo = "";
    }

    public UserDefinition(String name, String email, String phoneNo, String city, String backupEmail, String backupPhoneNo) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.city = city;
        this.backupEmail = backupEmail;
        this.backupPhoneNo = backupPhoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBackupEmail() {
        return backupEmail;
    }

    public void setBackupEmail(String backupEmail) {
        this.backupEmail = backupEmail;
    }

    public String getBackupPhoneNo() {
        return backupPhoneNo;
    }

    public void setBackupPhoneNo(String backupPhoneNo) {
        this.backupPhoneNo = backupPhoneNo;
    }

}
