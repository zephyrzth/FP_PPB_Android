package com.example.sejamu;

import com.google.gson.annotations.SerializedName;

public class PostPutDelItem {

    @SerializedName("status")
    String status;
    @SerializedName("data")
    DataItem dataItem;
    @SerializedName("message")
    String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDataItem(DataItem dataItem) {
        this.dataItem = dataItem;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public DataItem getDataItem() {
        return dataItem;
    }

    public String getMessage() {
        return message;
    }
}
