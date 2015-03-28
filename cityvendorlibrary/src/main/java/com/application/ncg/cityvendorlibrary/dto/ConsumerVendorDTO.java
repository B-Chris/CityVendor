/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.ncg.cityvendorlibrary.dto;



import java.io.Serializable;

/**
 *
 * @author Chris
 */
public class ConsumerVendorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer consumervendorID, vendorID, consumerID;
    private VendorDTO vendor;
    private ConsumerDTO consumer;

    public ConsumerVendorDTO() {
    }


    public Integer getConsumervendorID() {
        return consumervendorID;
    }

    public void setConsumervendorID(Integer consumervendorID) {
        this.consumervendorID = consumervendorID;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public Integer getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(Integer consumerID) {
        this.consumerID = consumerID;
    }

    public VendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorDTO vendor) {
        this.vendor = vendor;
    }

    public ConsumerDTO getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerDTO consumer) {
        this.consumer = consumer;
    }
    
    
    
}
