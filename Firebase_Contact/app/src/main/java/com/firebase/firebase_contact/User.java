package com.firebase.firebase_contact;

import java.io.Serializable;

/**
 * Created by vav on 4/11/16.
 */
public class User implements Serializable{
    public String email, fullName, password, phoneNumber, picture;

    public User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User(String email, String fullName, String password, String PhoneNumber, String picture) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = PhoneNumber;
        this.picture = picture;
    }


}


