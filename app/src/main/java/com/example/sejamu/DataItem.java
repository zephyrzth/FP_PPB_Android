package com.example.sejamu;

import com.google.gson.annotations.SerializedName;

public class DataItem {

    @SerializedName("id")
    private int id;
    @SerializedName("judul")
    private String judul;
    @SerializedName("harga")
    private int harga;
    @SerializedName("owner")
    private String owner;
    @SerializedName("filename")
    private String filename;
    @SerializedName("path")
    private String path;
    @SerializedName("photofile")
    private String photofile;

    public DataItem() { }

    public DataItem(int id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPhotofile(String photofile) {
        this.photofile = photofile;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public int getHarga() {
        return harga;
    }

    public String getOwner() {
        return owner;
    }

    public String getFilename() {
        return filename;
    }

    public String getPath() {
        return path;
    }

    public String getPhotofile() {
        return photofile;
    }
}
