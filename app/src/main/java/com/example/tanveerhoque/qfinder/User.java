package com.example.tanveerhoque.qfinder;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name")
    private String mname;
    @SerializedName("organization")
    private String morganization;
    @SerializedName("phone")
    private String mphone;
    @SerializedName("email")
    private String memail;
    @SerializedName("address")
    private String maddress;
    @SerializedName("facebook")
    private String mfacebook;

    public User(String name, String organization, String phone, String email, String address, String facebook) {
        this.mname = name;
        this.morganization = organization;
        this.mphone = phone;
        this.memail = email;
        this.maddress = address;
        this.mfacebook = facebook;
    }
}
