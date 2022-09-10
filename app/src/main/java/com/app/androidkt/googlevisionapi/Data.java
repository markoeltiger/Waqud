package com.app.androidkt.googlevisionapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("companyId")
    @Expose
    private String companyId;
    @SerializedName("carId")
    @Expose
    private String carId;
    @SerializedName("litre")
    @Expose
    private String litre;
    @SerializedName("pound")
    @Expose
    private String pound;
    @SerializedName("ekramyat")
    @Expose
    private String ekramyat;
    @SerializedName("all_costs")
    @Expose
    private String allCosts;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("all_kilometers")
    @Expose
    private String allKilometers;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getLitre() {
        return litre;
    }

    public void setLitre(String litre) {
        this.litre = litre;
    }

    public String getPound() {
        return pound;
    }

    public void setPound(String pound) {
        this.pound = pound;
    }

    public String getEkramyat() {
        return ekramyat;
    }

    public void setEkramyat(String ekramyat) {
        this.ekramyat = ekramyat;
    }

    public String getAllCosts() {
        return allCosts;
    }

    public void setAllCosts(String allCosts) {
        this.allCosts = allCosts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAllKilometers() {
        return allKilometers;
    }

    public void setAllKilometers(String allKilometers) {
        this.allKilometers = allKilometers;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }}