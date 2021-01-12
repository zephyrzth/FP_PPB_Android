package com.example.sejamu;

import com.google.gson.annotations.SerializedName;

public class PostPutDelUser {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    UserItem dataUser;
    @SerializedName("message")
    String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDataItem(UserItem dataItem) {
        this.dataUser = dataUser;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public UserItem getDataItem() {
        return dataUser;
    }

    public String getMessage() {
        return message;
    }
}
