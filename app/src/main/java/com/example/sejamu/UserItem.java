package com.example.sejamu;

import com.google.gson.annotations.SerializedName;

public class UserItem {

    public static String MY_TOKEN;
    public static int MY_ID;

    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;

    public UserItem() { }

    public UserItem(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNama(String nama) {
        this.name = nama;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNama() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public String getToken() {
        return token;
    }
}
