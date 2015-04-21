package com.application.ncg.cityvendorlibrary.util;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Chris on 2015-03-12.
 */
public class VendorLocation implements Serializable {

    private Integer vendorID;
    private double latitude, longitude;
    private float accuracy;
    private Date dateTaken;

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
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

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

}
