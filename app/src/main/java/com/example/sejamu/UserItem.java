package com.example.sejamu;

import com.google.gson.annotations.SerializedName;

public class UserItem {

    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("nama")
    private String nama;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    public static String MYTOKEN;

    public UserItem() { }

    public UserItem(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNama() {
        return nama;
    }

    public String getPassword() {
        return password;
    }
}
