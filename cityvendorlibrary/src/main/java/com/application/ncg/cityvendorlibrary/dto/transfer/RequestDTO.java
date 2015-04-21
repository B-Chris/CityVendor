/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.ncg.cityvendorlibrary.dto.transfer;

import com.application.ncg.cityvendorlibrary.dto.ConsumerDTO;
import com.application.ncg.cityvendorlibrary.dto.ConsumerVendorDTO;
import com.application.ncg.cityvendorlibrary.dto.GcmDeviceDTO;
import com.application.ncg.cityvendorlibrary.dto.ProductImageDTO;
import com.application.ncg.cityvendorlibrary.dto.ProductsDTO;
import com.application.ncg.cityvendorlibrary.dto.ProductsProductImageDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;

import java.io.Serializable;

/**
 *
 * @author Chris
 */
public class RequestDTO implements Serializable{
    private Integer requestType, consumerID, consumerVendorID, productImageID, productsID, 
            productsProductImageID, vendorID;
    private Boolean responseRequested, useHttp = false;
    private String email, name, pin, gcmRegistrationID;
    private Double latitude, longitude;
    private Float accuracy;
    private ConsumerDTO consumer;
    private ConsumerVendorDTO consumerVendor;
    private ProductImageDTO productImage;
    private ProductsDTO products;
    private ProductsProductImageDTO productsProductImage;
    private VendorDTO vendor;
    private GcmDeviceDTO gcmDevice;
    
    //register
    public static final int 
            REGISTER_CONSUMER = 1,
            REGISTER_VENDOR = 2;
    
    //add
    public static final int
            ADD_PRODUCTS = 10,
            ADD_PRODUCT_IMAGE = 11,
            ADD_PRODUCTS_PRODUCT_IMAGE = 12,
            ADD_CONSUMER_VENDOR = 13;
            
    //update
    public static final int
            UPDATE_CONSUMER = 20,
            UPDATE_CONSUMER_VENDOR = 21,
            UPDATE_PRODUCT_IMAGE = 22,
            UPDATE_PRODUCTS = 23,
            UPDATE_PRODUCTS_PRODUCT_IMAGE = 24,
            UPDATE_VENDOR = 25;
    
    //remove
    public static final int
            REMOVE_CONSUMER = 30,
            REMOVE_CONSUMER_VENDOR = 31,
            REMOVE_PRODUCT_IMAGE = 32,
            REMOVE_PRODUCTS = 33,
            REMOVE_PRODUCTS_PRODUCT_IMAGE = 34,
            REMOVE_VENDOR = 35;
    
    //gets
    public static final int
            GET_CONSUMER_BY_VENDOR = 40,
            GET_CONSUMER_VENDOR = 41,
            GET_PRODUCT_IMAGE_BY_PRODUCT = 42,
            GET_PRODUCTS = 43,
            GET_ALL_PRODUCTS_PRODUCT_IMAGE = 44,
            GET_VENDOR =45, 
            GET_CONSUMER = 46;

    public static final int
            LOGIN = 51,
            SEND_GCM_REGISTRATION = 50,
            CONFIRM_LOCATION = 52;


    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Integer getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(Integer consumerID) {
        this.consumerID = consumerID;
    }

    public Integer getConsumerVendorID() {
        return consumerVendorID;
    }

    public void setConsumerVendorID(Integer consumerVendorID) {
        this.consumerVendorID = consumerVendorID;
    }

    public Integer getProductImageID() {
        return productImageID;
    }

    public void setProductImageID(Integer productImageID) {
        this.productImageID = productImageID;
    }

    public Integer getProductsID() {
        return productsID;
    }

    public void setProductsID(Integer productsID) {
        this.productsID = productsID;
    }

    public Integer getProductsProductImageID() {
        return productsProductImageID;
    }

    public void setProductsProductImageID(Integer productsProductImageID) {
        this.productsProductImageID = productsProductImageID;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ConsumerDTO getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerDTO consumer) {
        this.consumer = consumer;
    }

    public ConsumerVendorDTO getConsumerVendor() {
        return consumerVendor;
    }

    public void setConsumerVendor(ConsumerVendorDTO consumerVendor) {
        this.consumerVendor = consumerVendor;
    }

    public ProductImageDTO getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImageDTO productImage) {
        this.productImage = productImage;
    }

    public ProductsDTO getProducts() {
        return products;
    }

    public void setProducts(ProductsDTO products) {
        this.products = products;
    }

    public ProductsProductImageDTO getProductsProductImage() {
        return productsProductImage;
    }

    public void setProductsProductImage(ProductsProductImageDTO productsProductImage) {
        this.productsProductImage = productsProductImage;
    }

    public VendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorDTO vendor) {
        this.vendor = vendor;
    }

    public String getGcmRegistrationID() {
        return gcmRegistrationID;
    }

    public void setGcmRegistrationID(String gcmRegistrationID) {
        this.gcmRegistrationID = gcmRegistrationID;
    }

    public Boolean getResponseRequested() {
        return responseRequested;
    }

    public void setResponseRequested(Boolean responseRequested) {
        this.responseRequested = responseRequested;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Boolean getUseHttp() {
        return useHttp;
    }

    public void setUseHttp(Boolean useHttp) {
        this.useHttp = useHttp;
    }

    public static int getConfirmLocation() {
        return CONFIRM_LOCATION;
    }
}
