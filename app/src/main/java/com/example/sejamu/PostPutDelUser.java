package com.example.sejamu;

import com.google.gson.annotations.SerializedName;

public class PostPutDelUser {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    DataItem dataUser;
    @SerializedName("message")
    String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDataItem(DataItem dataItem) {
        this.dataUser = dataUser;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public DataItem getDataItem() {
        return dataUser;
    }

    public String getMessage() {
        return message;
    }
}
