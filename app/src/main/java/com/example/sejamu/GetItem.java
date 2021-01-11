package com.example.sejamu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetItem {

    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<DataItem> listDataItem;
    @SerializedName("message")
    String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setListDataItem(List<DataItem> listDataItem) {
        this.listDataItem = listDataItem;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public List<DataItem> getListDataItem() {
        return listDataItem;
    }

    public String getMessage() {
        return message;
    }
}

