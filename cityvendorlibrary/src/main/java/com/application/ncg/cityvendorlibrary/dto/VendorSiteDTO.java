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
public class VendorSiteDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer vendorSiteID, vendorID, gcmDeviceID, locationConfirmed;
    private String vendorSiteName;
    private float accuracy;
    private double latitude;
    private double longitude;
    private Integer activeFlag;
    private GcmDeviceDTO gcmDevice;
    private VendorDTO vendor;
    private List<VendorDTO> vendorList = new ArrayList<>();

    public VendorSiteDTO() {
    }

    public Integer getLocationConfirmed() {
        return locationConfirmed;
    }

    public void setLocationConfirmed(Integer locationConfirmed) {
        this.locationConfirmed = locationConfirmed;
    }

    public Integer getVendorSiteID() {
        return vendorSiteID;
    }

    public void setVendorSiteID(Integer vendorSiteID) {
        this.vendorSiteID = vendorSiteID;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public Integer getGcmDeviceID() {
        return gcmDeviceID;
    }

    public void setGcmDeviceID(Integer gcmDeviceID) {
        this.gcmDeviceID = gcmDeviceID;
    }

    public String getVendorSiteName() {
        return vendorSiteName;
    }

    public void setVendorSiteName(String vendorSiteName) {
        this.vendorSiteName = vendorSiteName;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    public GcmDeviceDTO getGcmDevice() {
        return gcmDevice;
    }

    public void setGcmDevice(GcmDeviceDTO gcmDevice) {
        this.gcmDevice = gcmDevice;
    }

    public VendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorDTO vendor) {
        this.vendor = vendor;
    }

    public List<VendorDTO> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<VendorDTO> vendorList) {
        this.vendorList = vendorList;
    }
}
