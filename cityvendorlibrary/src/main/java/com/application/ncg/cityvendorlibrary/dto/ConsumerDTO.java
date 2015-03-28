/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.ncg.cityvendorlibrary.dto;


import com.application.ncg.cityvendorlibrary.dto.transfer.PhotoUploadDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris
 */
public class ConsumerDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer consumerID, productsID;
    private String name;
    private String email;
    private String pin;
    private Double latitude;
    private Double longitude;
    private String vehicleType;
    private String vehicleColor;
    private List<ConsumerVendorDTO> consumervendorList = new ArrayList<>();
    private List<PhotoUploadDTO> photouploadList = new ArrayList<>();

    public ConsumerDTO() {
    }



    public Integer getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(Integer consumerID) {
        this.consumerID = consumerID;
    }

    public Integer getProductsID() {
        return productsID;
    }

    public void setProductsID(Integer productsID) {
        this.productsID = productsID;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public List<ConsumerVendorDTO> getConsumervendorList() {
        return consumervendorList;
    }

    public void setConsumervendorList(List<ConsumerVendorDTO> consumervendorList) {
        this.consumervendorList = consumervendorList;
    }

    public List<PhotoUploadDTO> getPhotouploadList() {
        return photouploadList;
    }

    public void setPhotouploadList(List<PhotoUploadDTO> photouploadList) {
        this.photouploadList = photouploadList;
    }
}
