package com.onisha.oldaid.reminder.model;

import io.realm.RealmObject;

/**
 * Created by onisha on 15-Aug-16.
 */
public class RegistrationModelDB extends RealmObject {


    private String name, email, mobileNumber;


    public RegistrationModelDB() {
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
