/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.ncg.cityvendorlibrary.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris
 */
public class VendorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer vendorID, productsID;
    private String name;
    private String surname;
    private Double latitude;
    private Double longitude;
    private String streetAddress;
    private String email;
    private ProductsDTO products;
    private List<ConsumerVendorDTO> consumervendorList = new ArrayList<>();

    public VendorDTO() {
    }

    public VendorDTO(Integer vendorID, Integer productsID, String name, String surname, Double latitude, Double longitude, String streetAddress, String email, ProductsDTO products, List<ConsumerVendorDTO> consumervendorList) {
        this.vendorID = vendorID;
        this.productsID = productsID;
        this.name = name;
        this.surname = surname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetAddress = streetAddress;
        this.email = email;
        this.products = products;
        this.consumervendorList = consumervendorList;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public List<ConsumerVendorDTO> getConsumervendorList() {
        return consumervendorList;
    }

    public void setConsumervendorList(List<ConsumerVendorDTO> consumervendorList) {
        this.consumervendorList = consumervendorList;
    }

    @Override
    public String toString() {
        return "VendorDTO{" +
                "vendorID=" + vendorID +
                ", productsID=" + productsID +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", streetAddress='" + streetAddress + '\'' +
                ", email='" + email + '\'' +
                ", products=" + products +
                ", consumervendorList=" + consumervendorList +
                '}';
    }
}
